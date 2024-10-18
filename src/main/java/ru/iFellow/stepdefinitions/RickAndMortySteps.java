package ru.iFellow.stepdefinitions;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import io.qameta.allure.Step;
import ru.iFellow.step.RickAndMorty;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class RickAndMortySteps {

    private RickAndMorty service;
    private JSONObject mortyResponse;
    private JSONObject morty;
    private String mortyRace;
    private String mortyLocationUrl;
    private JSONArray episodeUrls;
    private List<Integer> episodeIds;
    private int lastEpisodeId;
    private String lastEpisodeUrl;
    private JSONObject lastEpisode;
    private JSONArray charactersInLastEpisode;
    private String lastCharacterUrl;
    private JSONObject lastCharacter;
    private String lastCharacterRace;
    private String lastCharacterLocationUrl;
    private JSONObject mortyLocation;
    private JSONObject lastCharacterLocation;
    private String mortyLocationName;
    private String lastCharacterLocationName;

    public RickAndMortySteps() {
        System.out.println("RickAndMortySteps инициализирован");
    }

    @Step("Получаем персонажа {name}")
    @Когда("я получаю персонажа {string}")
    public void i_fetch_the_character(String name) {
        System.out.println("Шаг @Когда: Получение персонажа");
        service = new RickAndMorty();
        mortyResponse = service.getCharacterByName(name);
    }

    @Step("Проверяем, что персонаж существует")
    @Тогда("персонаж должен существовать")
    public void the_character_should_exist() {
        System.out.println("Шаг @Тогда: Проверка существования персонажа");
        JSONArray results = mortyResponse.getJSONArray("results");
        Assertions.assertTrue(results.length() > 0, "Нет результатов для персонажа");
        morty = results.getJSONObject(0);
    }

    @Step("Получаем расу Морти")
    @И("раса Морти получена")
    public void mortys_species_is_retrieved() {
        System.out.println("Шаг @И: Получение расы Морти");
        mortyRace = morty.getString("species");
        mortyLocationUrl = morty.getJSONObject("location").optString("url", "");
    }

    @Step("Получаем местоположение Морти")
    @Когда("я получаю местоположение Морти")
    public void i_fetch_mortys_location() {
        System.out.println("Шаг @Когда: Получение местоположения Морти");
        mortyLocation = mortyLocationUrl.isEmpty() ? new JSONObject() : service.getLocationByUrl(mortyLocationUrl);
    }

    @Step("Получаем последний эпизод, в котором появился Морти")
    @И("я получаю последний эпизод, в котором появился Морти")
    public void i_fetch_the_last_episode_morty_appeared_in() {
        System.out.println("Шаг @И: Получение последнего эпизода Морти");
        episodeUrls = morty.getJSONArray("episode");
        Assertions.assertTrue(episodeUrls.length() > 0, "Морти не появляется ни в одном эпизоде");

        episodeIds = episodeUrls.toList().stream()
                .map(Object::toString)
                .map(url -> {
                    String[] parts = url.split("/");
                    return Integer.parseInt(parts[parts.length - 1]);
                })
                .collect(Collectors.toList());

        OptionalInt maxEpisodeIdOpt = episodeIds.stream().mapToInt(Integer::intValue).max();
        Assertions.assertTrue(maxEpisodeIdOpt.isPresent(), "Не удалось найти максимальный ID эпизода");
        lastEpisodeId = maxEpisodeIdOpt.getAsInt();
        lastEpisodeUrl = RickAndMorty.BASE_URL + "/episode/" + lastEpisodeId;
        lastEpisode = service.getEpisodeByUrl(lastEpisodeUrl);
    }

    @Step("Получаем последнего персонажа в эпизоде")
    @И("я получаю последнего персонажа в этом эпизоде")
    public void i_fetch_the_last_character_in_that_episode() {
        System.out.println("Шаг @И: Получение последнего персонажа в эпизоде");
        charactersInLastEpisode = lastEpisode.getJSONArray("characters");
        Assertions.assertTrue(charactersInLastEpisode.length() > 0, "В последнем эпизоде нет персонажей");
        lastCharacterUrl = charactersInLastEpisode.getString(charactersInLastEpisode.length() - 1);
        lastCharacter = service.getCharacterByUrl(lastCharacterUrl);
        lastCharacterRace = lastCharacter.getString("species");
        lastCharacterLocationUrl = lastCharacter.getJSONObject("location").optString("url", "");
        lastCharacterLocation = lastCharacterLocationUrl.isEmpty() ? new JSONObject() : service.getLocationByUrl(lastCharacterLocationUrl);
    }

    @Step("Проверяем, что расы Морти и последнего персонажа совпадают")
    @Тогда("расы Морти и последнего персонажа должны совпадать")
    public void the_races_of_morty_and_the_last_character_should_match() {
        System.out.println("Шаг @Тогда: Проверка совпадения рас Морти и последнего персонажа");
        Assertions.assertEquals(mortyRace, lastCharacterRace, "Расы персонажей не совпадают");
    }

    @Step("Проверяем, что местоположения Морти и последнего персонажа не совпадают")
    @И("местоположения Морти и последнего персонажа не должны совпадать")
    public void the_locations_of_morty_and_the_last_character_should_not_match() {
        System.out.println("Шаг @И: Проверка несоответствия местоположений Морти и последнего персонажа");
        mortyLocationName = mortyLocation.optString("name", "Unknown");
        lastCharacterLocationName = lastCharacterLocation.optString("name", "Unknown");
        Assertions.assertNotEquals(mortyLocationName, lastCharacterLocationName, "Местоположения персонажей совпадают");
    }
}
