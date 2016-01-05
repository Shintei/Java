import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.*;
interface HashFuntion<AnyType>
{
    int hashCode(AnyType x);
}
class MyHashMap<KeyType, ValueType> implements Iterable<Map.Entry<KeyType, ValueType>>
{
    private HashFunction<KeyType> hash1; // super ?
   
    public MyHashMap( )
    {
        this(null);
    }
   
    public int size( )
    {
        return theSize;
    }
    
    public void clear( )
    {
        doClear( );
    }
   
    private void doClear( )
    {
        theSize = 0;
        arr = new Node[ DEFAULT_ARRAY_SIZE ];
    }
   
    private void rehash( )
    {
        MyHashMap<KeyType , ValueType> bigger = new MyHashMap<KeyType , ValueType>( );
        bigger.arr = new Node[ arr.length * 2 ];
       
        for( Node<KeyType , ValueType> lst : arr )
            for( Node<KeyType , ValueType> p = lst; p != null; p = p.next )
                bigger.put( p.key );
       
        arr = bigger.arr;
        bigger = null;
    }
   
    public ValueType put( KeyType k, ValueType v )
    {
        if( size( ) > arr.length )
            rehash( );
       
        int whichList = myHash( k );
       
        for( Node<KeyType , ValueType> p = arr[ whichList ]; p != null; p = p.next )
            if( p.key.equals( k ) ) {
                ValueType old = p.value;
                p.value = v;
                return old;
            }
       
        // item not yet there; safe to add
        arr[ whichList ] = new Node<>( x, arr[ whichList ] );
        ++theSize;
       
        return true;
    }
   
    public boolean remove( KeyType k )
    {
        int whichList = myHash( k );
       
        // No items
        if( arr[ whichList ] == null )
            return false;
       
        // First item
        if( arr[ whichList ].key.equals( k ) )
        {
            arr[ whichList ] = arr[ whichList ].next;  // point to the second
            --theSize;
            return true;
        }
           
        // Second or later items
        for( Node<KeyType , ValueType> p = arr[ whichList ]; p.next != null; p = p.next )
            if( p.next.key.equals( k ) )
            {
                p.next = p.next.next;
                --theSize;
                return true;
            }
       
        return false;
    }
   
    private int myHash( KeyType k )
    {
        if ( hash1 == null)
        return Math.abs( k.hashCode( ) % arr.length );
        else
            return Math.abs( k.hashCode( ) % arr.length );
    }
   
    public boolean get( KeyType k , ValueType v )
    {
        int whichList = myHash( k );
       
        for( Node<KeyType , ValueType> p = arr[ whichList ]; p != null; p = p.next )
            if( p.key.equals( k ) )
                return true;
       
        return null;
    }
   
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );
       
        /*
        for( Node<AnyType> lst : arr )
            for( Node<AnyType> p = lst; p != null; p = p.next )
                sb.append( p.data + " " );
                */
        for( KeyType s : this )
        {
            System.out.println( s );
            sb.append( s + " " );
        }
        sb.append( "]" );
       
        return new String( sb );
    }
   
    public Iterator<KeyType , ValueType> iterator( )
    {
        return new Iterator<Map.Entry<KeyType, ValueType>>( )
        {
            public boolean hasNext( )
            {
                return current != null;
            }
           
            public Map.Entry<KeyType, ValueType> next( )
            {
                final Node<KeyType , ValueType> theCurrent = current;
                current = current.next;
               
                if( current == null )
                {
                    listNum++;
                    advanceToNewList( );
                }
               
               return new Map.Entry<KeyType, ValueType> ()
               {
                   public KeyType getKey(){
                       return theCurrent.key;
                   }
                   public ValueType getValue(){
                       return theCurrent.value;
                   }
                   public ValueType setValue(ValueType newValue){
                       throw new UnsupportedOperationException("Not supported yet.");
                   }
                  
               }
            }
           
            private void advanceToNewList( )
            {
                while( listNum < arr.length && arr[ listNum ] == null )
                    listNum++;
               
                if( listNum != arr.length )  // current is already null
                    current = arr[ listNum ];
            }
           
            {  // instance initializer (like a constructor)
                advanceToNewList( );
            }
           
            Node<KeyType , ValueType> current;   // current node
            int listNum;                // current list #
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
   
    private static class Node<KeyType, ValueType>
    {
        Node( KeyType k,  ValueType v, Node<KeyType, ValueType> n )
        { key = k; next = n; value = v;}
       
        public String toString(){
            return key + '=' ;
        }
        KeyType key;
        ValueType value;
        Node<KeyType, ValueType> next;
    }
   
    private static final int DEFAULT_ARRAY_SIZE = 11;
   
    private Node<KeyType, ValueType> [ ] arr = null;
    private int theSize = 0;
   
}
public class July14
{
    public static int MyHashCode(String str)
    {
        int result = 0;
       
        for (int i = 0; i < str.length(); i++)
            result = result * 37 + str.charAt(i);
           
            return result;
       
    }
   
    public static void main( String [ ] args )
    {
        MyHashMap<String, Integer> s1 = new MyHashMap<>( );
        HashSet<String> s2 = new HashSet<>( );
        List<String> arr = new ArrayList<>( );
        Random r = new Random( );
       
        int N = 10;
        int LEN = 9;
        char [ ] alpha = { 'a', 'b', 'c', 'd', 'e' };
       
        for( int i = 0; i < N; ++i )
        {
            String s = "";
            for( int j = 0; j < LEN; ++j )
                s += alpha[ r.nextInt( 5 ) ];
           
            arr.add( s );
        }
       
        long start, end;
       
        start = System.currentTimeMillis( );
        for( String s : arr )
            s1.add( s );
        end = System.currentTimeMillis( );
        System.out.println( ( end - start ) + "ms  (me)" );
       
        start = System.currentTimeMillis( );
        for( String s : arr )
            s2.add( s );
        end = System.currentTimeMillis( );
        System.out.println( ( end - start ) + "ms  (Java)" );
       
        System.out.println( s1.size( ) + " " + s2.size( ) );
        System.out.println( s1 );
        System.out.println( s2 );
       
        start = System.currentTimeMillis( );
        for( String s : arr )
            s1.remove( s );
        end = System.currentTimeMillis( );
        System.out.println( ( end - start ) + "ms  (me)" );
       
        start = System.currentTimeMillis( );
        for( String s : arr )
            s2.remove( s );
        end = System.currentTimeMillis( );
        System.out.println( ( end - start ) + "ms  (Java)" );
       
        System.out.println( s1.size( ) + " " + s2.size( ) );
    }
}
