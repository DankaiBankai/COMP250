package hashMap;


public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		if(this.head == null) {
			head = new HashNode<K,V>(key, value);
			this.size++;
		}else { // adds to front of Llist
			HashNode<K,V> oldHead = this.head;
			this.head = new HashNode<K,V>(key, value);
			this.head.next = oldHead;
			this.size++;
			
//			HashNode<K,V> tmpNode = this.head;     //Adds to end of LList
//			while(tmpNode.next != null) {
//				tmpNode = tmpNode.next;
//			}
//			tmpNode.next = new HashNode<K,V>(key, value);
//			this.size++;
		}
		

		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE
		if (this.head != null) {
			if(this.head.getKey().equals(key)) {
				return this.head;
			}else {
				HashNode<K,V> tmpNode = this.head;
				while(tmpNode.next != null  && !(tmpNode.next.getKey().equals(key))) {
					tmpNode = tmpNode.next;
				}
				if (tmpNode.next == null) {
					return null;
				}else {
					return tmpNode.next;
				}
			}
		}return null;
		
		
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
		HashNode<K,V> tmpNode = this.head; //test this
		if (tmpNode != null) {
			this.head = (this.head).next;
			this.size--;
		}
		
		return tmpNode;
		// ADD CODE ABOVE HERE
		
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE
		if (this.head != null && this.head.getKey().equals(key)) {
			return this.removeFirst();
		}else if(this.head != null){
			
			HashNode<K,V> tmpNode = this.head;
			while(tmpNode.next != null && !(tmpNode.next.getKey().equals(key))) {
				tmpNode = tmpNode.next;
			}
			
			if (tmpNode.next !=null) {
				HashNode<K,V> nodeToRemove = tmpNode.next;
				if (tmpNode.next.next != null) {
					
					tmpNode.next = (nodeToRemove).next;
					nodeToRemove.next = null;
					this.size--;
					return nodeToRemove;
					
				}else if (tmpNode.next.next == null){
					
					tmpNode.next = null;
					this.size--;
					return nodeToRemove;
				}
			}
		}
		// ADD CODE ABOVE HERE
		return null; // removing failed
	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	public HashNode<K,V> getHead(){
		return this.head;
	}
	
	public void incSize() {
		this.size++;
	}

	//ADD YOUR HELPER METHODS ABOVE THIS


}
