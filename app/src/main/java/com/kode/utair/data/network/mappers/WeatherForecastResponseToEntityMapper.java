package com.kode.utair.data.network.mappers;

import com.kode.utair.data.network.responses.WeatherForecastResponse;
import com.kode.utair.domain.commons.Mapper;
import com.kode.utair.domain.models.WeatherForecastEntity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class WeatherForecastResponseToEntityMapper extends Mapper<WeatherForecastResponse, WeatherForecastEntity> {

    @Inject
    public WeatherForecastResponseToEntityMapper() {
    }

    public WeatherForecastEntity map(WeatherForecastResponse response) {
        List<WeatherForecastEntity.HourlyForecast> hourlyForecastEntities = new ArrayList<>();
        List<WeatherForecastResponse.HourlyForecast> hourlyForecastResponses = response.getHourlyForecasts();

        for (WeatherForecastResponse.HourlyForecast hourlyForecastResponse : hourlyForecastResponses) {
            //переводим из unix timestamp в дату, умножаем на тысячу для перевода секунд в миллисекунды
            DateTime dateTime = new DateTime(hourlyForecastResponse.getTimestamp() * 1000);
            WeatherForecastEntity.WeatherCondition condition = getConditionById(hourlyForecastResponse.getWeatherId());
            float temperature = hourlyForecastResponse.getTemperature();
            float speed = hourlyForecastResponse.getSpeed();
            hourlyForecastEntities.add(new WeatherForecastEntity.HourlyForecast(dateTime, condition, temperature, speed));
        }

        List<WeatherForecastEntity.DailyForecast> dailyForecastEntities = splitForecastsByDays(hourlyForecastEntities);

        return new WeatherForecastEntity(response.getCityName(), dailyForecastEntities);
    }

    /**
     * Возвращает погодные условия по идентификатору
     * Подробнее об иденетификаторах погодных условий можно узнать здесь https://openweathermap.org/weather-conditions
     */
    private WeatherForecastEntity.WeatherCondition getConditionById(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return WeatherForecastEntity.WeatherCondition.STORM;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return WeatherForecastEntity.WeatherCondition.LIGHT_RAIN;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return WeatherForecastEntity.WeatherCondition.RAIN;
        } else if (weatherId == 511) {
            return WeatherForecastEntity.WeatherCondition.SNOW;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return WeatherForecastEntity.WeatherCondition.RAIN;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return WeatherForecastEntity.WeatherCondition.SNOW;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return WeatherForecastEntity.WeatherCondition.FOG;
        } else if (weatherId == 761 || weatherId == 781) {
            return WeatherForecastEntity.WeatherCondition.STORM;
        } else if (weatherId == 800) {
            return WeatherForecastEntity.WeatherCondition.SUNNY;
        } else if (weatherId == 801) {
            return WeatherForecastEntity.WeatherCondition.LIGHT_CLOUDS;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return WeatherForecastEntity.WeatherCondition.CLOUDS;
        }
        return WeatherForecastEntity.WeatherCondition.UNKNOWN;
    }

    /**
     * Разбиват почасовой прогноз по дням
     */
    private List<WeatherForecastEntity.DailyForecast> splitForecastsByDays(List<WeatherForecastEntity.HourlyForecast> hourlyForecastEntities) {
        //сортируем почасовой прогноз по возрастанию времени
        Collections.sort(hourlyForecastEntities, (forecast1, forecast2) -> forecast1.getDateTime().compareTo(forecast2.getDateTime()));

        Map<String, WeatherForecastEntity.DailyForecast> forecastsByDay = new HashMap<>();
        for (WeatherForecastEntity.HourlyForecast hourlyForecast : hourlyForecastEntities) {
            DateTime forecastDate = hourlyForecast.getDateTime();
            String dateStringKey = forecastDate.getDayOfMonth() + "." + forecastDate.getMonthOfYear() + "." + forecastDate.getYear();
            WeatherForecastEntity.DailyForecast dailyForecast = forecastsByDay.get(dateStringKey);
            if (dailyForecast == null) {
                dailyForecast = new WeatherForecastEntity.DailyForecast(new DateTime(forecastDate));
                forecastsByDay.put(dateStringKey, dailyForecast);
            }
            dailyForecast.addHourForecast(hourlyForecast);
        }

        List<WeatherForecastEntity.DailyForecast> dailyForecastEntities = new ArrayList<>(forecastsByDay.values());

        //сортируем прогноз по дате
        Collections.sort(dailyForecastEntities, (forecast1, forecast2) -> forecast1.getDate().compareTo(forecast2.getDate()));
        return dailyForecastEntities;
    }


}
