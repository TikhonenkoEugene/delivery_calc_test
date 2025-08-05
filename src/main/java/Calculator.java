public class Calculator {

    /**
     *
     * @param distance - Расстояние в километрах
     * @param dimensions - размеры в метрах (если одна из сторон больше 1 м, то груз считается большим
     * @param isFragile - признак хрупкости груза, true - груз хрупкий
     * @param workload - коэффициент загруженности службы доставки
     * @return - итоговая цена доставки при заданных аргументах функции
     */
    public double getPriceOfDelivery(int distance, Dimensions dimensions, boolean isFragile, double workload) {
        if (distance > 30 && isFragile) {
            throw new RuntimeException("Хрупкие грузы нельзя возить на расстояние более 30 км");
        }
        double addCostByDistance = distance <= 2 ? 50.0 : distance <= 10 ? 100.0 :
                distance <= 30 ? 200.0 : 300.0;
        double addCostByDimensions = dimensions.getHeight() >= 1.00 || dimensions.getLength() >= 1.00 ||
                dimensions.getWidth() >= 1.00 ? 200.0 : 100.0;
        double addCostByFragile = isFragile ? 300.00 : 0.00;
        double sumAllAccruals = addCostByDistance + addCostByDimensions + addCostByFragile;
        double result = workload <= 1 ? sumAllAccruals * 1 : sumAllAccruals * workload;
        return Math.round(Math.max(result, 400.0) * 100.0) / 100.0;
    }
}
