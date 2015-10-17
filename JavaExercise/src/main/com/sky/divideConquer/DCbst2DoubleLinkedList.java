package com.sky.divideConquer;

import com.loic.algo.tree.TreeNode;
import com.sky.problem.OneInputOneOutputProb.BST2DoublyLinkedListProb;

public class DCbst2DoubleLinkedList implements BST2DoublyLinkedListProb
{

	@Override
	public TreeNode resolve(TreeNode bstNode)
	{
		return changeToLinkList(bstNode);
	}

	private TreeNode changeToLinkList(TreeNode node)
	{
		if(node == null)
		{
			return null;
		}
		else if (node.mLeftNode == null && node.mRightNode == null) 
		{
			return node;
		}
		else 
		{
			TreeNode leftLinkList = changeToLinkList(node.mLeftNode);
			TreeNode rightLinkList = changeToLinkList(node.mRightNode);
			TreeNode retVal = leftLinkList == null ? node : leftLinkList;
			while(leftLinkList != null && leftLinkList.mRightNode != null)
			{
				leftLinkList = leftLinkList.mRightNode;
			}
			node.mLeftNode = leftLinkList;
			node.mRightNode = rightLinkList;
			if(leftLinkList != null)
			{
				leftLinkList.mRightNode = node;
			}
			if(rightLinkList != null)
			{
				rightLinkList.mLeftNode = node;
			}
			return retVal;
		}
	}
}