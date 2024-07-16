package org.karabulut.collection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class FullList<T> implements Iterable<T>{
    private final int m_Capasity = 10;
    private T [] m_FirstList;
    private T [] m_LastList;
    private T [] m_Middle;
    private T [] m_Full;

    private int m_Idx_first;
    private int m_Idx_last;
    private int m_Idx_middle;
    private int m_Id_middle = 0;
    private int m_Id_first = 0;
    private int m_Id_last = 0;
    public FullList() {
        this.m_FirstList = (T[]) new Object[m_Capasity];
        this.m_LastList = (T[]) new Object[m_Capasity];
        this.m_Middle = (T[]) new Object[m_Capasity];

        m_Idx_middle = 0;
        m_Idx_first = 0;
        m_Idx_last = 0;
    }

    @Override
    public Iterator<T> iterator() {
        var sum = m_Idx_first + m_Idx_middle + m_Idx_last;
        int m_First = 0;
        int m_last = 0;
        int m_n = 0;
        this.m_Full = (T[]) new Object[sum];
        for (int i = 0; i < sum; ++i)
        {
            if (m_First < m_Idx_first)
            {
                m_Full[i] = m_FirstList[m_First++];

            }
            else if (m_last < m_Idx_last)
            {
                m_Full[i] = m_LastList[m_last++];
            }
            else
                m_Full[i] = m_Middle[m_n++];
        }

        return new Iterator<T>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < sum  ;
            }

            @Override
            public T next() {
                if (count > sum)
                    throw  new NoSuchElementException("Index %d out of bounds for length %d ".formatted(count,sum));


                return m_Full[count++];
            }
        };

    }

    private void controlerFull()
    {
        if (m_Id_middle == m_Capasity)
        {
            m_Middle = Arrays.copyOf(m_Middle,m_Capasity * 2);
            m_Id_middle = 0;
        }

    }
    private void controlerLast()
    {
        if (m_Id_last == m_Capasity)
        {
            m_LastList = Arrays.copyOf(m_LastList,m_Capasity * 2);
            m_Id_last = 0;
        }

    }
    private void controlerFirst()
    {
        if (m_Id_first == m_Capasity)
        {
            m_FirstList = Arrays.copyOf(m_FirstList,m_Capasity * 2);
            m_Id_first = 0;
        }

    }
    public void addFirst(T t)
    {
        ++m_Id_first;
        controlerFirst();
        m_FirstList[m_Idx_first++] = t;
    }

    public void addLast(T t)
    {
        ++m_Id_last;
        controlerLast();
        m_LastList[m_Idx_last++] = t;
    }
    public void add(T t)
    {
        ++m_Id_middle;
        controlerFull();
        m_Middle[m_Idx_middle++] = t;
    }
    public void add(int i,T t)
    {

        if (i >= m_Idx_middle - 1)
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_middle));

        controlerFull();
        swap(i,t);
    }
    public void addFirst(int i,T t)
    {

        if (i >= m_Idx_first- 1)
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_first));

        controlerFirst();
        swapFirst(i,t);
    }
    public void addLast(int i, T t)
    {

        if (i >= m_Idx_last- 1)
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_last));

        controlerLast();
        swapLast(i,t);
    }
    public void addAll(FullList<T> t)
    {

        for (int i = 0; i <=  t.m_Idx_middle - 1; ++i)
        {
            ++m_Id_middle;
            controlerFull();
            m_Middle[m_Idx_middle++] = t.get(i);

        }


    }

    public void addAllFirst(FullList<T> t)
    {

        for (int i = 0; i <=  t.m_Idx_first - 1; ++i)
        {
            ++m_Id_first;
            controlerFirst();
            m_FirstList[m_Idx_first++] = t.getFirst(i);

        }


    }
    public void addAllLast(FullList<T> t)
    {

        for (int i = 0; i <=  t.m_Idx_last - 1; ++i)
        {
            ++m_Id_last;
            controlerLast();
            m_LastList[m_Idx_last++] = t.getLast(i);

        }


    }
    private void swap(int i, T t)
    {

        var temp = m_Middle[m_Idx_middle - 1];

        for (int j = m_Idx_middle -1; j >= i; j--) {
            m_Middle[j + 1] = m_Middle[j];
        }
        m_Middle[i] = t;
        m_Middle[++m_Idx_middle] = temp;
    }
    private void swapFirst(int i, T t)
    {

        var temp = m_FirstList[m_Idx_first - 1];

        for (int j = m_Id_first -1 ; j >= i; j--) {
            m_FirstList[j + 1] = m_FirstList[j];
        }
        m_FirstList[i] = t;
        m_FirstList[++m_Idx_first] = temp;
    }
    private void swapLast(int i, T t)
    {
        var temp = m_LastList[m_Idx_last - 1];

        for (int j = m_Idx_last - 1; j >= i; j--)
        {
            m_LastList[j + 1 ] = m_LastList[j];
        }
        m_LastList[i] = temp;
        m_LastList[++m_Idx_last] = temp;
    }


    public int length()
    {
        return m_Idx_middle;
    }

    public int lengthFirst()
    {
        return m_Idx_first;
    }
    public int lengthLength()
    {
        return m_Idx_last;
    }
    public T getFirst(int i)
    {
        return m_FirstList[i];
    }
    public T getLast(int i)
    {

        if (i > m_LastList.length)
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d out of bounds for length %d ",i, m_LastList.length));

        return m_LastList[i];
    }
    public T getfirst(int i)
    {

        if (i > m_FirstList.length)
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d out of bounds for length %d ",i, m_FirstList.length));

        return m_FirstList[i];
    }
    public T get(int i)
    {

        if (i > m_Middle.length)
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d out of bounds for length %d ",i, m_Middle.length));

        return m_Middle[i];
    }

    public int size()
    {
        return m_Idx_middle;
    }
    public int sizeFirst()
    {
        return m_Idx_first;
    }
    public int sizeLast()
    {
        return m_Idx_last;
    }

    public T set(int i , T t)
    {
        var temp = m_Middle[i];
        m_Middle[i] = t;
        return temp;
    }
    public T setFirst(int i , T t)
    {
        var temp = m_FirstList[i];
        m_FirstList[i] = t;
        return temp;
    }
    public T setlast(int i , T t)
    {
        var temp = m_LastList[i];
        m_LastList[i] = t;
        return temp;
    }
    public T remove(int i)
    {
        if (i >= m_Idx_middle)
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_middle));
        var temp = m_Middle[i];

        for (int j = i - 1; j <= m_Idx_middle; j++) {
            m_Middle[j + 1] = m_Middle[m_Idx_middle - 1];
        }
        m_Middle[m_Idx_middle--] = null;
        return temp;
    }

    public T removeFirst(int i)
    {
        if (i >= m_Idx_first )
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_first));
        var temp = m_FirstList[i];

        for (int j = i - 1; j <= m_Idx_first; j++) {
            m_FirstList[j + 1] = m_FirstList[m_Idx_first - 1];
        }

        m_FirstList[--m_Idx_first] = null;
        return temp;
    }
    public T removeLast(int i)
    {
        if (i >= m_Idx_last )
            throw new IllegalArgumentException(String.format("Index %d out of bounds for length %d ",i, m_Idx_last));
        var temp = m_LastList[i];

        for (int j = i - 1; j <= m_Id_last; j++) {

            m_LastList[j] = m_LastList[m_Id_last - 1];

        }
        m_LastList[--m_Idx_last] = null;
        return temp;

    }

    public boolean isEmpty()
    {
        return m_Idx_middle == 0;
    }
    public boolean isEmptyFirst()
    {
        return m_Idx_middle == 0;
    }
    public boolean isEmptyLast()
    {
        return m_Idx_middle == 0;
    }
    public boolean contains(T t)
    {
        for (int i = 0; i < m_Idx_middle; i++)
        {
            if (m_Middle[i].equals(t))
                return true;
        }

            return false;

    }
    public boolean containsFirst(T t)
    {
        for (int i = 0; i < m_Idx_first; i++)
        {
            if (m_FirstList[i].equals(t))
                return true;
        }

        return false;

    }
    public boolean containsLast(T t)
    {
        for (int i = 0; i < m_Idx_last; i++)
        {
            if (m_LastList[i].equals(t))
                return true;
        }

        return false;

    }

    public void clear()
    {

        this.m_Middle = (T[]) new Object[m_Capasity];
        m_Idx_middle = 0;
        m_Id_middle = 0;
    }
    public void clearFirst()
    {

        this.m_FirstList = (T[]) new Object[m_Capasity];
        m_Idx_first = 0;
        m_Id_first = 0;
    }
    public void clearLast()
    {

        this.m_LastList = (T[]) new Object[m_Capasity];
        m_Idx_last = 0;
        m_Id_last = 0;
    }
    public void clearAll()
    {

        this.m_Middle = (T[]) new Object[m_Capasity];
        this.m_FirstList = (T[]) new Object[m_Capasity];
        this.m_LastList = (T[]) new Object[m_Capasity];
        m_Idx_middle = 0;
        m_Id_middle = 0;
        m_Idx_first = 0;
        m_Id_first = 0;
        m_Idx_last = 0;
        m_Id_last = 0;
    }

    public int indexOf(T o)
    {
        var val = IntStream.range(0, m_Idx_middle - 1).filter(i -> m_Middle[i].equals(o))
                .findFirst();

                return val.isEmpty() ? -1 : val.getAsInt();
    }
    public int indexOfFirst(T o)
    {
        var val = IntStream.range(0,m_Idx_first).filter(i -> m_FirstList[i].equals(o)).findFirst();

        return val.isEmpty() ? -1 : val.getAsInt();
    }
    public int indexOfLast(T o)
    {
        var val = IntStream.range(0,m_Idx_last).filter(i -> m_LastList[i].equals(o)).findFirst();

        return val.isEmpty() ? -1 : val.getAsInt();
    }

    public int digitSum()
    {

       return Arrays.stream(m_Middle).filter(b -> b instanceof Integer).mapToInt(a -> (Integer) a).sum();
    }

    public int digitSumFirst()
    {
        return Arrays.stream(m_FirstList).filter(b -> b instanceof Integer).mapToInt(a -> (Integer) a).sum();
    }
    public int digitSumLast()
    {
        return Arrays.stream(m_LastList).filter(b -> b instanceof Integer).mapToInt(a -> (Integer) a).sum();
    }
    public void distinct()
    {

        var temp = (T[]) Arrays.stream(m_Middle).distinct().toArray();
        clear();
        for (T t : temp)
        {

            m_Middle[m_Idx_middle++] = t;
            m_Id_middle++;
        }
       m_Middle[--m_Idx_middle] = null;

    }
    public void distinctFirst()
    {
        var temp = (T[] )Arrays.stream(m_FirstList).distinct().toArray();
        clearFirst();

        for (T t : temp)
        {
            m_FirstList[m_Idx_first++] = t;
            m_Id_first++;
        }
        m_FirstList[--m_Idx_first] = null;
    }
    public void distinctLast()
    {

        var temp = (T[]) Arrays.stream(m_LastList).distinct().toArray();
        clear();
        for (T t : temp)
        {

            m_LastList[m_Idx_last++] = t;
            m_Id_last++;
        }
        m_LastList[--m_Idx_last] = null;

    }

    ////

}