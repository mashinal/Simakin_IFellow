package ru.iFellow.test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iFellow.step.RickAndMorty;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static ru.iFellow.step.RickAndMorty.BASE_URL;

public class RickAndMortyTest {

    public void testMortyLastEpisodeAndCharacter() {
        RickAndMorty service = new RickAndMorty();

        JSONObject mortyResponse = service.getCharacterByName("Morty Smith");
        JSONArray results = mortyResponse.getJSONArray("results");
        Assertions.assertTrue(results.length() > 0, "Нет результатов для Морти Смит");

        JSONObject morty = results.getJSONObject(0);
        String mortyRace = morty.getString("species");
        String mortyLocationUrl = morty.getJSONObject("location").optString("url", "");

        JSONArray episodeUrls = morty.getJSONArray("episode");
        Assertions.assertTrue(episodeUrls.length() > 0, "Морти не появляется ни в одном эпизоде");

        List<Integer> episodeIds = (List<Integer>) episodeUrls.toList().stream()
                .map(Object::toString)
                .map(url -> {
                    String[] parts = url.split("/");
                    return Integer.parseInt(parts[parts.length - 1]);
                })
                .collect(Collectors.toList());

        OptionalInt maxEpisodeId = episodeIds.stream().mapToInt(Integer::intValue).max();
        Assertions.assertTrue(maxEpisodeId.isPresent(), "Не удалось найти максимальный ID эпизода");

        int lastEpisodeId = maxEpisodeId.getAsInt();
        String lastEpisodeUrl = BASE_URL + "/episode/" + lastEpisodeId;

        JSONObject lastEpisode = service.getEpisodeByUrl(lastEpisodeUrl);

        JSONArray charactersInLastEpisode = lastEpisode.getJSONArray("characters");
        Assertions.assertTrue(charactersInLastEpisode.length() > 0, "В последнем эпизоде нет персонажей");

        String lastCharacterUrl = charactersInLastEpisode.getString(charactersInLastEpisode.length() - 1);
        JSONObject lastCharacter = service.getCharacterByUrl(lastCharacterUrl);
        String lastCharacterRace = lastCharacter.getString("species");
        String lastCharacterLocationUrl = lastCharacter.getJSONObject("location").optString("url", "");

        JSONObject mortyLocation = mortyLocationUrl.isEmpty() ? new JSONObject() : service.getLocationByUrl(mortyLocationUrl);
        JSONObject lastCharacterLocation = lastCharacterLocationUrl.isEmpty() ? new JSONObject() : service.getLocationByUrl(lastCharacterLocationUrl);

        String mortyLocationName = mortyLocation.optString("name", "Unknown");
        String lastCharacterLocationName = lastCharacterLocation.optString("name", "Unknown");

        boolean sameRace = mortyRace.equalsIgnoreCase(lastCharacterRace);
        boolean sameLocation = mortyLocationName.equalsIgnoreCase(lastCharacterLocationName);

        System.out.println("Раса Морти: " + mortyRace);
        System.out.println("Раса последнего персонажа: " + lastCharacterRace);
        System.out.println("Местоположение Морти: " + mortyLocationName);
        System.out.println("Местоположение последнего персонажа: " + lastCharacterLocationName);

        Assertions.assertTrue(sameRace, "Расы персонажей не совпадают");
        Assertions.assertNotEquals(sameLocation, "Местоположения персонажей совпадают");
    }
}
