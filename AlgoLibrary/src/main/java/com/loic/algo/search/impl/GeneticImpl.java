package com.loic.algo.search.impl;

import static com.loic.algo.search.TreeSearchUtils.asStringSort;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import com.loic.algo.search.Timer;
import com.loic.algo.search.TreeSearchUtils;
import com.loic.algo.search.core.SearchParam;
import com.loic.algo.search.core.TreeSearch;
import com.loic.algo.search.genetic.CandidateSolver;
import com.loic.algo.search.genetic.GeneticAlgorithm;

public class GeneticImpl implements TreeSearch {
    private static final int MAX_SAME_FITNESS_GENERATION = 7;
    private static final float DEFAULT_MUTATION_RATE = 0.1f;

    private final Timer timer = new Timer();

    private int population = 100;
    private int simuCount = 100;

    @Override
    public <Trans, State> Optional<Trans> find(State root, SearchParam<Trans, State> param) {
        requireNonNull(root, "Root state is mandatory");
        requireNonNull(param, "SearchParam is mandatory");

        Optional<Trans> next = TreeSearchUtils.nextTrans(root, param.transitionStrategy());
        if (next != null) return next;

        Resolver<Trans, State> resolver = new Resolver<>(root, param);
        Gene<Trans> best = new GeneticAlgorithm<>(resolver).iterate(simuCount, population, 10, 50, 25);

        return Optional.of(best.trans[0]);
    }

    private static final class Resolver<Trans, State> implements CandidateSolver<Gene<Trans>> {
        private final State root;
        private final SearchParam<Trans, State> param;

        private Resolver(State root, SearchParam<Trans, State> param) {
            this.root = root;
            this.param = param;
        }

        @Override
        public Gene<Trans> generateRandomly(Random random) {
            Trans[] trans = null;
            State curState = root;

            for (int i = 0; i < param.getMaxDepth(); i++) {
                List<Trans> possibleTrans = asStringSort(param.transitionStrategy().generate(curState));
                if (possibleTrans.isEmpty()) break;
                if (trans == null) {
                    trans = (Trans[]) Array.newInstance(possibleTrans.get(0).getClass(), param.getMaxDepth());
                }
                trans[i] = possibleTrans.get(random.nextInt(possibleTrans.size()));
                curState = param.applyStrategy().apply(curState, trans[i]);
            }

            return new Gene<>(trans);
        }

        @Override
        public double heuristic(Gene<Trans> transGene) {
            State curState = root;
            int depth = 0;
            for (int i = 0; i < param.getMaxDepth(); i++) {
                List<Trans> possibleTrans = asStringSort(param.transitionStrategy().generate(curState));
                if (!possibleTrans.contains(transGene.trans[i])) break;

                curState = param.applyStrategy().apply(curState, transGene.trans[i]);
                depth++;
            }
            return param.heuristicStrategy().heuristic(curState, depth);
        }

        @Override
        public Gene<Trans> merge(Gene<Trans> gene1, Gene<Trans> gene2, Random random) {
            Trans[] newTrans = (Trans[]) Array.newInstance(gene1.trans[0].getClass(), param.getMaxDepth());
            IntStream.range(0, param.getMaxDepth())
                .forEach(index -> newTrans[index] = random.nextBoolean() ? gene1.trans[index] : gene2.trans[index]);
            return new Gene<>(newTrans);
        }

        @Override
        public Gene<Trans> mutate(Gene<Trans> transGene, Random random) {
            return transGene;
        }
    }

    private static final class Gene<Trans> {
        private final Trans[] trans;

        private Gene(Trans[] trans) {
            this.trans = Objects.requireNonNull(trans);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Gene<?> gene = (Gene<?>) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(trans, gene.trans);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(trans);
        }
    }
}