package ru.tilacyn.smartList;

import com.sun.istack.internal.NotNull;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;

public class SmartList<E> extends AbstractList<E> {
    private int size = 0;
    private Object data = null;

    public SmartList() {
    }


    public SmartList(Collection<? extends E> collection) {
        if (collection.size() == 0) {
            return;
        }
        if (collection.size() == 1) {
            for (E e : collection) {
                data = e;
            }
        }
        if (collection.size() <= 5) {
            data = (E[]) new Object[5];

            int index = 0;

            for (E e : collection) {
                ((E[]) data)[index] = e;
                index++;
            }
        }
        if (collection.size() > 5) {
            data = new ArrayList<E>();
            ((ArrayList<E>) data).addAll(collection);
        }
    }

    @Override
    public E get(int index) {
        if (size == 1) {
            return (E) data;
        } else if (size >= 2 && size <= 5) {
            return ((E[]) data)[index];
        } else {
            return ((ArrayList<E>) data).get(index);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NotNull Object o) {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            return o.equals(data);
        } else if (size <= 5) {
            System.out.println(size + " Wow " + ((E[]) data).length);
            for (E elem : (E[]) data) {
                if (elem == null) {
                    continue;
                }
                if (elem.equals(o)) {
                    return true;
                }
            }
            return false;
        } else {
            return ((ArrayList<E>) data).contains(o);
        }
    }

    @Override
    public void add(int position, @NotNull E element) {
        if (position < 0 || position > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (size == 0) {
            data = element;
        } else if (size == 1) {
            E firstValue = (E) data;


            data = (E[]) new Object[5];

            if (position == 1) {
                ((E[]) data)[0] = firstValue;
                ((E[]) data)[1] = element;
            } else {
                ((E[]) data)[1] = firstValue;
                ((E[]) data)[2] = element;
            }

        } else if (size <= 5) {
            if (size < 5) {
                System.out.println(size);
                for (int i = size; i > position; i--) {
                    ((E[]) data)[i] = ((E[]) data)[i - 1];
                }
                ((E[]) data)[position] = element;
            } else {
                ArrayList<E> array = new ArrayList<>();
                for (E e : ((E[]) data)) {
                    array.add(e);
                }

                array.add(position, element);
            }
        } else {
            ((ArrayList<E>) data).add(position, element);
        }

        size++;
    }

    @Override
    public boolean add(@NotNull E element) {
        add(size, element);
        return true;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        E result = null;

        if (size == 1) {
            result = (E) data;
            data = element;
        } else if (size <= 5 && size >= 2) {
            result = ((E[]) data)[index];
            ((E[]) data)[index] = element;
        } else {
            result = ((ArrayList<E>) data).set(index, element);
        }
        return result;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        E result = null;

        if (size == 1) {
            result = ((E[]) data)[0];
        } else if (size <= 5 && size > 2) {
            result = ((E[]) data)[index];
            for (int i = index; i < size - 1; i++) {
                ((E[]) data)[i] = ((E[]) data)[i + 1];
            }
        } else if (size == 2) {
            if (index == 0) {
                result = ((E[]) data)[0];
                data = ((E[]) data)[1];
            }
            if (index == 1) {
                result = ((E[]) data)[1];
                data = ((E[]) data)[0];
            }
        } else if (size == 6) {
            ((ArrayList<E>) data).remove(index);
            data = ((ArrayList<E>) data).toArray();
        }

        return result;
    }


}
