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

import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractFactoryTest {

    enum Fruit {
        apple, orange
    }

    enum Animal {
        cat, dog
    }

    enum Unknown {
        unk
    }

    final class Fuji {}
    final class Cat {
        protected final String name;

        Cat(final String name) {
            this.name = name;
        }
    }

    final class FruitFactory extends AbstractFactory<Fruit> {

        @Override
        protected Class<Fruit> instanceOfCatalogue() {
            return Fruit.class;
        }

        @Override
        protected <T> T createInstanceByFactory(final Fruit value, final Object ... args) {
            switch(value) {
                case apple:
                    return (T)new Fuji();
                default:
                    throw new IllegalArgumentException(format("Factory does not know how to create %1$s", value.toString()));
            }
        }
    }

    final class AnimalFactory extends AbstractFactory<Animal> {

        @Override
        protected Class<Animal> instanceOfCatalogue() {
            return Animal.class;
        }

        @Override
        protected <T> T createInstanceByFactory(final Animal value, final Object ... args) {
            switch(value) {
                case cat:
                    final String name = args != null ? (args.length > 0 ? args[0].toString() : null) : null;
                    return (T)new Cat(name);
                default:
                    throw new IllegalArgumentException(format("Factory does not know how to create %1$s", value.toString()));
            }
        }
    }

    final class CombinationFactory extends AbstractFactory<Unknown> {
        CombinationFactory() {
            super(new AnimalFactory(), new FruitFactory());
        }

        @Override
        protected Class<Unknown> instanceOfCatalogue() {
            return Unknown.class;
        }

        @Override
        protected <T> T createInstanceByFactory(Unknown value, Object... args) {
            return null;
        }
    }

    FruitFactory fruitFactory;

    AnimalFactory animalFactory;

    CombinationFactory combinationFactory;

    @Before
    public void before() {
        fruitFactory = new FruitFactory();
        animalFactory = new AnimalFactory();
        combinationFactory = new CombinationFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenInstanceIsUnknown() {
        fruitFactory.getInstance(Unknown.unk);
    }

    @Test
    public void shouldReturnFalseWhenInstanceIsUnknown() {
        final boolean result = fruitFactory.isSupported(Unknown.unk);
        assertFalse(result);
    }

    @Test
    public void isSupportedShouldReturnFalseIfValueIsNotSupported() {
        final boolean result = fruitFactory.isSupported(Animal.cat);

        assertFalse(result);
    }

    @Test
    public void importFactoryShouldAllowImportingFactoryToSupportInstancesDefinedInImportedFactory() {
        final boolean preImportResult = fruitFactory.isSupported(Animal.cat);

        fruitFactory.importFactory(animalFactory);

        final boolean result = fruitFactory.isSupported(Animal.cat);

        assertFalse(preImportResult);
        assertTrue(result);
    }

    @Test
    public void importFactoryShouldAllowImportingFactoryToProvideInstancesDefinedInImportedFactory() {
        fruitFactory.importFactory(animalFactory);

        final Cat result = fruitFactory.getInstance(Animal.cat);

        assertNotNull(result);
    }

    @Test
    public void getInstanceWithArgsShouldBePassedToConstructor() {
        final String name = "name";
        final Cat result = animalFactory.getInstance(Animal.cat, name);

        assertNotNull(result);
        assertThat(result.name, is(name));
    }

    @Test
    public void multipleFactoryConstructorWillImportFactories() {
        final boolean resultSupportsAnimal = combinationFactory.isSupported(Animal.cat);
        final boolean resultSupportsFruit = combinationFactory.isSupported(Fruit.apple);

        final Cat resultAnimal = combinationFactory.getInstance(Animal.cat);
        final Fuji resultFruit = combinationFactory.getInstance(Fruit.apple);

        assertTrue(resultSupportsAnimal);
        assertTrue(resultSupportsFruit);

        assertNotNull(resultAnimal);
        assertNotNull(resultFruit);
    }

}
