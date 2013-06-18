/**
 *
 * Copyright (c) 2011 Matthew D Huckaby. All rights reservered.
 * ------------------------------------------------------------------------------------
 * Image2Css is licensed under Apache 2.0, please see LICENSE file.
 *
 * Use of this software indicates you agree to the following as well :
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * This product includes software developed by The Apache Software Foundation (http://www.apache.org/).
 * ------------------------------------------------------------------------------------
 */
package com.rf1m.image2css.cmn.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;


public abstract class AbstractFactory <C extends Enum> {
    public static final String METHOD_VALUES = "values";

    final Set<AbstractFactory> factories;

    public AbstractFactory() {
        this.factories = new HashSet<AbstractFactory>();
    }


    protected AbstractFactory(final AbstractFactory ... factories) {
        this.factories = new HashSet<AbstractFactory>();
        this.factories.addAll(Arrays.asList(factories));
    }

    public <T> T getInstance(final Enum value, final Object ... args) {
        final boolean assignableFrom = value.getClass().isAssignableFrom(this.instanceOfCatalogue());

        if(assignableFrom && this.isSupported(value)) {
            final Object result = this.createInstanceByFactory((C)value, args);

            if(null == result) {
                throw new IllegalArgumentException(format("Factory does not know how to create %1$s", value.toString()));
            }else {
                return (T)result;
            }
        }else {
            return this.getInstanceFromImportedFactory(value);
        }
    }

    public void importFactory(final AbstractFactory factory) {
        this.factories.add(factory);
    }

    public boolean isSupported(final Enum value) {
        final Class clazz = this.instanceOfCatalogue();
        final Method methodValues = this.getMethodValues(clazz);
        final Enum[] values = this.invokeMethodValues(value, methodValues);

        for(final Enum element : values) {
            if(element.equals(value)) {
                return true;
            }else {
                continue;
            }
        }

        return this.isSupportedByImport(value);
    }

    protected abstract <T> T createInstanceByFactory(final C value, final Object ... args);

    protected abstract Class<C> instanceOfCatalogue();

    protected <T> T getInstanceFromImportedFactory(final Enum value) {
        final AbstractFactory supportingImportedFactory = this.getSupportingFactory(value);

        if(null == supportingImportedFactory) {
            throw new IllegalArgumentException(format("Factory does not know how to create %1$s", value.toString()));
        }else {
            return (T)supportingImportedFactory.getInstance(value);
        }
    }

    protected final Method getMethodValues(final Class clazz) {
        try {
            return clazz.getDeclaredMethod(METHOD_VALUES);
        }catch(final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    protected AbstractFactory getSupportingFactory(final Enum value) {
        for(final AbstractFactory factory : this.factories) {
            if(factory.isSupported(value)) {
                return factory;
            }else {
                continue;
            }
        }

        return null;
    }

    protected boolean isSupportedByImport(final Enum value) {
        return null != this.getSupportingFactory(value);
    }

    protected final Enum[] invokeMethodValues(final Enum value, final Method methodValues) {
        try {
            return (Enum[])methodValues.invoke(value);
        }catch(final InvocationTargetException e) {
            throw new RuntimeException(e);
        }catch(final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}