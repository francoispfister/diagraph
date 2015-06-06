/******************************************************************************
 * KIELER - Kiel Integrated Environment for Layout for the Eclipse RCP
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2009 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
//orig package de.cau.cs.kieler.core.util;
package org.isoe.fwk.jobs;



/**
 * Object that may contain another object, inspired by the Haskell type <i>Maybe</i>.
 * <p>
 * This class can be used to wrap objects for anonymous classes:
 * <pre>
 * Myclass foo() {
 *     final Maybe<Myclass> maybe = new Maybe<Myclass>();
 *     PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
 *         public void run() {
 *             maybe.set(new Myclass("bar"));
 *         }
 *     });
 *     return maybe.get();
 * }
 * </pre>
 * </p>
 * <p>
 * Another use is as a wrapper for synchronization on objects that may be {@code null}:
 * <pre>
 * Maybe<Myclass> maybe = new Maybe<Myclass>();
 * 
 * void thread1() {
 *     maybe.set(new Myclass("foo"));
 *     synchronized (maybe) {
 *         maybe.notify();
 *     )
 * }
 * 
 * void thread2() {
 *     synchronized (maybe) {
 *         if (maybe.get() == null) {
 *             maybe.wait();
 *         }
 *     }
 *     maybe.get().bar();
 * }
 * </pre>
 * </p>
 * 
 * @kieler.rating 2009-12-11 proposed yellow msp
 * @param <T> type of contained object
 * @author msp
 */
public class Maybe<T> {

    /** the contained object, which may be {@code null}. */
    private T object;
    
    /**
     * Creates a maybe without an object.
     */
    public Maybe() {
        this.setObject(null);
    }
    
    /**
     * Creates a maybe with the given object.
     * 
     * @param theobject the object to contain
     */
    public Maybe(final T theobject) {
        this.setObject(theobject);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Maybe<?>) {
            Maybe<?> other = (Maybe<?>)obj;
            return this.getObject() == null ? other.getObject() == null
                    : this.getObject().equals(other.getObject());
        } else {
            return false;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (getObject() == null) {
            return 0;
        } else {
            return getObject().hashCode();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (getObject() == null) {
            return "maybe(null)";
        } else {
            return "maybe(" + getObject().toString() + ")";
        }
    }

    /**
     * Sets the contained object.
     *
     * @param theobject the object to set
     */
    public void setObject(final T theobject) {
        this.object = theobject;
    }

    /**
     * Returns the contained object.
     *
     * @return the contained object
     */
    public T getObject() {
        return object;
    }
    
    
    /**
     * Create a maybe with inferred generic type.
     * 
     * @param <D> the generic type
     * @return a new instance of given type
     */
    public static <D> Maybe<D> create() {
        return new Maybe<D>();
    }



    /**
     * Sets the contained object.
     *
     * @param theobject the object to set
     */
    public void set(final T theobject) {
        this.object = theobject;
    }

    /**
     * Returns the contained object.
     *
     * @return the contained object
     */
    public T get() {
        return object;
    }
    
}
