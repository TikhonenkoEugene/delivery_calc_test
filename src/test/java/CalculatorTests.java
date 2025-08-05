import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTests {
    Calculator calculator = new Calculator();

    @Test
    public void test1() {
        // Проверяем что размеры определяются верно,
        // если хотя бы одна из сторон больше или равна 1 метру,
        // груз считается большим иначе маленьким

        Dimensions dimensions = new Dimensions(1.00, 0.90, 0.50); // Большой груз
        double actualResult = calculator.getPriceOfDelivery(20, dimensions, true, 1);
        Assertions.assertEquals(700.0, actualResult);

        dimensions = new Dimensions(0.60, 1.00, 0.20); // Большой груз
        actualResult = calculator.getPriceOfDelivery(20, dimensions, true, 1);
        Assertions.assertEquals(700.0, actualResult);

        dimensions = new Dimensions(0.60, 0.50, 1.00); // Большой груз
        actualResult = calculator.getPriceOfDelivery(20, dimensions, true, 1);
        Assertions.assertEquals(700.0, actualResult);

        dimensions = new Dimensions(1.00, 1.00, 1.00); // Большой груз
        actualResult = calculator.getPriceOfDelivery(20, dimensions, true, 1);
        Assertions.assertEquals(700.0, actualResult);

        // По скольку у нас в условии больше или равно 1 метра это большой груз,
        // то можно добавить проверку границ 1.1 метра, но для примера и так сойдет

        dimensions = new Dimensions(0.60, 0.50, 0.10); // Маленьгий груз
        actualResult = calculator.getPriceOfDelivery(20, dimensions, true, 1);
        Assertions.assertEquals(600.0, actualResult);
    }

    @Test
    public void test2() {
        // Проверяем увеличение цены за счет расстояния
        // более 30 км
        Dimensions dimensions = new Dimensions(1.00, 1.00, 1.00);
        double actualResult = calculator.getPriceOfDelivery(31, dimensions, false, 1.6);
        Assertions.assertEquals(800.0, actualResult);

        // до 30 км включительно
        actualResult = calculator.getPriceOfDelivery(30, dimensions, false, 1.6);
        Assertions.assertEquals(640.0, actualResult);

        // до 30 км
        actualResult = calculator.getPriceOfDelivery(11, dimensions, false, 1.6);
        Assertions.assertEquals(640.0, actualResult);

        // до 10 км включительно
        actualResult = calculator.getPriceOfDelivery(10, dimensions, true, 1.6);
        Assertions.assertEquals(960.0, actualResult);

        // до 10 км
        actualResult = calculator.getPriceOfDelivery(3, dimensions, true, 1.6);
        Assertions.assertEquals(960.0, actualResult);

        // до 2 км включительно
        actualResult = calculator.getPriceOfDelivery(2, dimensions, true, 1.6);
        Assertions.assertEquals(880.0, actualResult);
    }

    @Test
    public void test3() {
        // Проверяем увеличение цены за счет хрупкости
        // не хрупкий
        Dimensions dimensions = new Dimensions(1.00, 1.00, 1.00);
        double actualResult = calculator.getPriceOfDelivery(30, dimensions, false, 1.6);
        Assertions.assertEquals(640.0, actualResult);

        // хрупкий
        actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 1.0);
        Assertions.assertEquals(700.0, actualResult);

        // хрупкий более 30 км
        try {
            calculator.getPriceOfDelivery(31, dimensions, true, 1.0);
        }
        catch (RuntimeException exception) {
            Assertions.assertEquals(
                    "Хрупкие грузы нельзя возить на расстояние более 30 км", exception.getMessage());
        }
    }

    @Test
    public void test4() {
        // Проверяем увеличение цены за счет коэффициента загруженности
        Dimensions dimensions = new Dimensions(1.00, 1.00, 1.00);
        double actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 0.9);
        Assertions.assertEquals(700.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 1.0);
        Assertions.assertEquals(700.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 1.2);
        Assertions.assertEquals(840.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 1.4);
        Assertions.assertEquals(980.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(30, dimensions, true, 1.6);
        Assertions.assertEquals(1120.0, actualResult);
    }

    @Test
    public void test5() {
        // Проверяем минимальную цену доставки
        Dimensions dimensions = new Dimensions(1.00, 1.00, 1.00);
        double actualResult = calculator.getPriceOfDelivery(35, dimensions, false, 1.0);
        Assertions.assertEquals(500.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(30, dimensions, false, 1.0);
        Assertions.assertEquals(400.0, actualResult);

        actualResult = calculator.getPriceOfDelivery(2, dimensions, false, 1.0);
        Assertions.assertEquals(400.0, actualResult);
    }
}
