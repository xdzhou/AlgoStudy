package com.loic.daily.exercise;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DoctolibParser {
  private static final String DOCTOLIB_ROOT = "https://www.doctolib.fr";
  private static final JsonMapper JSON_MAPPER = new JsonMapper();

  public static void main(String... args) throws Exception {
    int pageLoadSize = 15;
    int loop = 10;
    DoctolibParser parser = new DoctolibParser();
    while (true) {
      List<Site> sites = parser.loadSites(pageLoadSize);
      parser.checkAvailabilities(loop, sites);
    }
  }

  private Map<String, String> cookies = Collections.emptyMap();

  private Connection.Response jsoupResponse(String url) throws IOException {
    return jsoupResponse(url, c -> {
    });
  }

  private Connection.Response jsoupResponse(String url, Consumer<Connection> consumer) throws IOException {
    Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
      .timeout(10_000)
      .cookies(cookies);
    consumer.accept(connection);
    Connection.Response response = connection.execute();
    cookies = response.cookies();
    return response;
  }

  private void checkAvailabilities(int loop, List<Site> sites) throws Exception {
    while (loop > 0 && sites.size() > 0) {
      System.out.printf("Scan %d sites in loop ...%d%n", sites.size(), loop);
      Iterator<Site> iterator = sites.iterator();
      while (iterator.hasNext()) {
        Site site = iterator.next();
        String url = DOCTOLIB_ROOT + "/search_results/" + site.id + ".json?ref_visit_motive_ids%5B%5D=6970&ref_visit_motive_ids%5B%5D=7005&speciality_id=5494&search_result_format=json&limit=3&force_max_limit=2";
        String json = null;
        try {
          json = jsoupResponse(url, c -> c.ignoreContentType(true)).body();
          JsonNode rootNode = JSON_MAPPER.readValue(json, JsonNode.class);
          JsonNode availNode = rootNode.get("availabilities");
          String zipCode = rootNode.get("search_result").get("zipcode").asText();
          if (eligible(zipCode)) {
            if (pickFirstSlot(availNode)) {
              Toolkit.getDefaultToolkit().beep();
              System.err.println(zipCode + " : " + site.name);
              System.out.println(DOCTOLIB_ROOT + site.url);
            }
          } else {
            iterator.remove();
          }
        } catch (Exception e) {
          e.printStackTrace();
          iterator.remove();
        }
        Thread.sleep(100);
      }
      loop--;
    }
  }

  private static boolean pickFirstSlot(JsonNode availsNode) {
    LocalDate today = LocalDate.now();
    for (int i = 0; i < availsNode.size(); i++) {
      JsonNode avail = availsNode.get(0);
      LocalDate date = LocalDate.parse(avail.get("date").asText());
      JsonNode slots = avail.get("slots");
      // only check today & tomorrow's slots
      if (slots.size() > 0 && (date.equals(today) || date.equals(today.plusDays(1)))) {
        JsonNode firstSlot = slots.get(0);
        System.out.println(firstSlot);
        return true;
      }
    }
    return false;
  }

  private static boolean eligible(String zipCode) {
    return zipCode.startsWith("92") || zipCode.startsWith("78") || zipCode.startsWith("95")
      || zipCode.startsWith("75") || zipCode.startsWith("91") || zipCode.startsWith("94");
  }

  private List<Site> loadSites(int pageLoadSize) throws IOException {
    List<Site> sites = new ArrayList<>();
    String firstUrl = "/vaccination-covid-19/courbevoie?force_max_limit=2&ref_visit_motive_id=6970&ref_visit_motive_ids%5B%5D=6970&ref_visit_motive_ids%5B%5D=7005";
    Optional<String> url = Optional.of(firstUrl);
    while (url.isPresent() && pageLoadSize > 0) {
      System.out.println("loading page ..." + pageLoadSize);
      url = loadSites(url.get(), sites);
      pageLoadSize--;
    }
    return sites;
  }

  private Optional<String> loadSites(String href, List<Site> sites) throws IOException {
    Document doc = jsoupResponse(DOCTOLIB_ROOT + href).parse();
    for (Element ele : doc.select("div.dl-search-result")) {
      String searchId = ele.attr("id").split("-")[2];
      Element nameEle = ele.selectFirst("a.dl-search-result-name");
      String url = nameEle.attr("href");
      String name = nameEle.child(0).text();
      Site site = new Site(name, searchId, url);
      fetchSiteIds(site);
      sites.add(site);
    }
    return Optional.ofNullable(doc.selectFirst("div.next"))
      .map(e -> e.child(0).attr("href"));
  }

  private void fetchSiteIds(Site site) throws IOException {
    Document siteRoot = jsoupResponse(DOCTOLIB_ROOT + site.url).parse();
    Element dataLayer = siteRoot.selectFirst("div#datalayer");
    JsonNode propsNode = JSON_MAPPER.readValue(dataLayer.attr("data-props"), JsonNode.class);
    site.profileId = propsNode.get("profile_id").asText();
    site.practiceId = propsNode.get("profile_practice_id").asText();
  }

  private static class Site {
    private final String name, id, url;
    private String profileId, practiceId;

    public Site(String name, String id, String url) {
      this.name = name;
      this.id = id;
      this.url = url;
    }
  }
}
