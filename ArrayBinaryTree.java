import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
/**
 * This class implements a binary tree by using an array.
 * 
 * @author Jacquelyn Schuler 
 */
 
public class ArrayBinaryTree<T>
             implements BinaryTreeInterface<T>,java.io.Serializable
{
    private T   theData[];
    private int height; // height of tree
    private int size;   // number of locations in array for a full tree of this height
    
    // Initializes an empty tree
    public ArrayBinaryTree()
    {
        theData = (T[]) new Object[0];
        height = 0;
        size = 0;
    } 

    // Initializes a tree with one node
    public ArrayBinaryTree(T rootData)
    {
        setTree(rootData);
    } 
    
    // Initializes a tree with rootData as the root and leftTree and 
    // rightTree as the left and right nodes
    public ArrayBinaryTree(T rootData, ArrayBinaryTree<T> leftTree,
                                       ArrayBinaryTree<T> rightTree)
    {
        if((leftTree == null) && (rightTree == null))
        {
            setTree(rootData);
        }
        else
        {
            setTree(rootData, (ArrayBinaryTree<T>) leftTree, 
                (ArrayBinaryTree<T>) rightTree); 
        }    
    } 

    /** Task: Sets an existing binary tree to a new one-node binary tree.
     *  @param rootData   an object that is the data in the new tree's root
     */
    public void setTree(T rootData)
    {
        // SET THE TREE TO BE A NEW ONE-NODE TREE
        theData = (T[]) new Object[1];
        height = 1;
        size = 1;
        
        theData[0] = rootData;    
    } 
    
    /** Task: Sets an existing binary tree to a new binary tree.
     *  @param rootData   an object that is the data in the new tree's root
     *  @param leftTree   the left subtree of the new tree
     *  @param rightTree  the right subtree of the new tree */    
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                                    BinaryTreeInterface<T> rightTree)
    {
        int currentIndex;
        
        if((leftTree == null) && (rightTree == null))
        {
            setTree(rootData);
        }
        
        else if((leftTree != null) && (rightTree != null))
        {
            ArrayBinaryTree<T> lTree = (ArrayBinaryTree<T>) leftTree; 
            ArrayBinaryTree<T> rTree = (ArrayBinaryTree<T>) rightTree; 
            
            height = Math.max(lTree.getHeight(), rTree.getHeight()) + 1;
            
            theData = (T[]) new Object[getSizeFromHeight(height)];
            
            setRootData(rootData);
            size = getSizeFromHeight(height);
                      
            setLeftSubtree((ArrayBinaryTree<T>) leftTree);
            setRightSubtree((ArrayBinaryTree<T>) rightTree);
        }
        
        else if((leftTree == null) && (rightTree != null))
        {
            ArrayBinaryTree<T> rTree = (ArrayBinaryTree<T>) rightTree; 

            height = rTree.getHeight() + 1;
            
            theData = (T[]) new Object[getSizeFromHeight(height)];
            
            setRootData(rootData);
            size = getSizeFromHeight(height);
            
            setRightSubtree((ArrayBinaryTree<T>) rightTree);
        }
        
        else if((leftTree != null) && (rightTree == null))
        {
            ArrayBinaryTree<T> lTree = (ArrayBinaryTree<T>) leftTree; 

            height = lTree.getHeight() + 1;
            
            theData = (T[]) new Object[getSizeFromHeight(height)];
                        
            setRootData(rootData);
            size = getSizeFromHeight(height);

            setLeftSubtree((ArrayBinaryTree<T>) leftTree);
        }
    } 

       
    /* 
	 * Finds the size of the array necessary to fit a tree of height h. 
     */
    private int getSizeFromHeight(int h)
    {
        // IT CALCULATES THE SIZE OF THE ARRAY NEEDED TO ACCOMODATE A TREE OF HEIGHT H
        return (int) Math.round(Math.pow(2.0, (double) h) - 1.0);
    }
        
    /*
     * Copies the data values from the given subtree into the leftsubtree.
     * Precondition: The array theData is large enough to hold the new values.
     */
    private void setLeftSubtree(ArrayBinaryTree<T> subTree)
    {
        // IT TAKES IN THE VALUES OF THE LEFT SUBTREE AND ADDS THEM TO THE ARRAY
        
        int subTreeIndex = 0;
        int nodesInRow = 1;
        int firstIndex = 0;
        int lastIndex = firstIndex + nodesInRow - 1;
        for(int a = 1; a <= subTree.height; a++)
        {
            firstIndex = 2 * firstIndex + 1;
            lastIndex = firstIndex + nodesInRow - 1;
            int currentIndex = firstIndex;

            for(int b = firstIndex; b <= lastIndex; b++)
            {
                theData[currentIndex] = subTree.theData[subTreeIndex];
                currentIndex++;
                subTreeIndex++;
            }
            
            nodesInRow = 2 * nodesInRow;
        }
    }
    
    /*
     * Copies the data values from the given subtree into the rightsubtree. 
     * Precondition: The array theData is large enough to hold the new values.
     */
    private void setRightSubtree(ArrayBinaryTree<T> subTree)
    {
        // IT TAKES IN THE VALUES OF THE LEFT SUBTREE AND ADDS THEM TO THE ARRAY
        
        int subTreeIndex = 0;
        int nodesInRow = 1;
        int firstIndex = 0;
        int lastIndex = 0;
        for(int a = 1; a <= subTree.height; a++)
        {
            lastIndex = 2 * lastIndex + 2;
            firstIndex = lastIndex - nodesInRow + 1;
            int currentIndex = firstIndex;

            for(int b = firstIndex; b <= lastIndex; b++)
            {
                theData[currentIndex] = subTree.theData[subTreeIndex];
                currentIndex++;
                subTreeIndex++;
            }
            
            nodesInRow = 2 * nodesInRow;
        }
    }
    
    public T getRootData()
    {
        // RETURNS THE ROOT OF THE TREE

        if(!isEmpty())
            return theData[0];
        else
            return null;
    } 
    
    public boolean isEmpty()
    {
        // RETURNS TRUE IF THE TREE IS EMPTY
        return (size == 0) && (height == 0);
    } 

    public void clear()
    {
        // EMPTIES THE TREE
        for(int i = 0; i < theData.length; i++)
        {
            theData[i] = null;
        }
        
        height = 0;
        size = 0;
    }

    protected void setRootData(T rootData)
    {
        // SETS THE ROOT OF THE TREE TO A NEW VALUE
        theData[0] = rootData;
    }

    public int getHeight()
    {
        // GETS THE HEIGHT OF THE TREE
        // HINT: YOU MIGHT WANT TO ADD A PRIVATE METHOD THAT HELPS FIND THE HEIGHT USING RECURSION

        if(this == null)
            return 0;
        else
            return (int) Math.round(Math.ceil((Math.log(size + 1)/Math.log(2.0))));
    }
       
    public int getNumberOfNodes()
    {
        // RETURNS THE NUMBER OF NODES IN THE TREE

        System.out.println("");
        for(int a = 0; a < theData.length; a++)
            System.out.print(theData[a] + " ");
        System.out.println("");
        
        int n = 0;
        
        for(int i = 0; i < theData.length; i++)
        {
            if(theData[i] != null)
            {
                n++;
            }
        }
        
        return n;
    } 
    
 
    /*
     * The following operations allow one to move in the tree and test to see
     * whether a child exists. These methods have already been implemented.
     */
    protected boolean hasLeftChild(int i)
    {
        return nodeExists( ( 2 * i + 1) );
    }
    protected int leftChild(int i)
    {
        return 2 * i + 1;
    }
        
    protected boolean hasRightChild(int i)
    {
        return nodeExists( ( 2 * i + 2) );
    }
    protected int rightChild(int i)
    {
        return 2 * i + 2;
    }    
    
    protected boolean nodeExists(int i)
    {
        return (i >= 0 && i < size)  && (theData[i] != null);
    }
    protected int parent(int i)
    {
        return (i - 1) / 2;
    }
    protected T getData(int i)
    {
        T result = null;
        
        if (nodeExists(i))
            result = theData[i];
        return result;
    }
    
    private void resizeArray()
    {
        // RESIZE THE ARRAY
        // FIND THE NEW SIZE NEEDED BASED ON THE HEIGHT
        // COPY THE CONTENTS OVER AND UPDATE HEIGHT AND SIZE
        
        T old[] = theData;
        int oldSize = old.length;
                
        height++;
        size = getSizeFromHeight(height);
        
        theData = (T[]) new Object[size];
                        
        if(old.length > 0)
        {
            for(int i = 0; i < old.length; i++)
            {
                theData[i] = old[i];
            }
        }
    }

    public Iterator<T> getInorderIterator()
    {
        return new InorderIterator();
    } 
    
    private class InorderIterator implements Iterator<T>
    {
        private Stack<Integer> nodeStack;
        private Integer currentNode;
        
        public InorderIterator()
        {
            nodeStack = new Stack<Integer>();
            currentNode = 0;
        } 
        
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || nodeExists(currentNode);
        } 
        
        public T next()
        {
            Integer nextNode = -1;
            
            // find leftmost node with no left child
            while (nodeExists(currentNode))
            {
                nodeStack.push(currentNode);
                currentNode = leftChild(currentNode);
            } 
                        
            // get leftmost node, then move to its right subtree
            if (!nodeStack.isEmpty())
            {
                nextNode = nodeStack.pop();
                assert nodeExists(nextNode); // since nodeStack was not empty before the pop
                currentNode = rightChild(nextNode); // right subchild
            }
            else
                throw new NoSuchElementException();
                
            return theData[nextNode];
        } 
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        } 
    } // end InorderIterator

    
    public Iterator<T> getPreorderIterator()
    {
        return new PreorderIterator();
    } // end getPreorderIterator
    
    private class PreorderIterator implements Iterator<T>
    {
        private Stack<Integer> nodeStack;
        
        public PreorderIterator()
        {
            nodeStack = new Stack<Integer>();
            if (!isEmpty())
                nodeStack.push(0);
        } // end default constructor
        
        public boolean hasNext()
        {
            return !nodeStack.isEmpty();
        } // end hasNext
        
        public T next()
        {
            T result = null;
            if (nodeStack.isEmpty())
            {
                throw new NoSuchElementException();
            }
            else
            {
                Integer top = nodeStack.pop();
                result = theData[top];
                
                // Push the children on the stack.  Right then left.
                
                if (hasRightChild(top))  // has right child
                    nodeStack.push(rightChild(top));
                if (hasLeftChild(top))  // has left child
                    nodeStack.push(leftChild(top));
            }
           
            return result;
        } // end next
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PreorderIterator
   
    public Iterator<T> getPostorderIterator()
    {
        return new PostorderIterator();
    } 
    
    private class PostorderIterator implements Iterator<T>
    {
        private Stack<PostOrderNode> nodeStack;
        
        public PostorderIterator()
        {
            nodeStack = new Stack<PostOrderNode>();
            if (!isEmpty())
                nodeStack.push(new PostOrderNode(0, PostOrderState.LEFT));
        } 
        
        public boolean hasNext()
        {
            return !nodeStack.isEmpty();
        } 
        
        public T next()
        {
            T result = null;
            if (nodeStack.isEmpty())
            {
                throw new NoSuchElementException();
            }
            else
            {
                PostOrderNode top = nodeStack.pop();
                PostOrderState state = top.state;
                
                while (state != PostOrderState.TOP)
                {
                    if (state == PostOrderState.LEFT)
                    {
                        top.state = PostOrderState.RIGHT;
                        nodeStack.push(top);
                        
                        if (hasLeftChild(top.node)) // hasLeftChild
                            nodeStack.push(new PostOrderNode(leftChild(top.node), PostOrderState.LEFT));
                    }
                    else
                    {
                        assert state == PostOrderState.RIGHT;
                        top.state = PostOrderState.TOP;
                        nodeStack.push(top);
                        
                        if (hasRightChild(top.node)) // hasRightChild
                            nodeStack.push(new PostOrderNode(rightChild(top.node), PostOrderState.LEFT));
                    }
                    top = nodeStack.pop();
                    state = top.state;
                }
                result = theData[top.node];
            }
           
            return result;
        } 
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        } 
    } // end PostorderIterator 
    
    private enum PostOrderState {TOP, LEFT, RIGHT};
    
    private class PostOrderNode
    {
        public Integer node;
        public PostOrderState state;
        
        PostOrderNode(Integer theNode, PostOrderState theState)
        {
            node = theNode;
            state = theState;
        }
    }
    
    public Iterator<T> getLevelOrderIterator()
    {
        throw new UnsupportedOperationException();
    } 
    
    /* display the contents of the array */
    public void display()
    {
        System.out.println(size);
        for (int i = 0; i < size; i++)
        {
            if (nodeExists(i))
                System.out.println("index: " + i + " has " + getData(i));
        }
    }
}   
    

