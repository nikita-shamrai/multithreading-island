package plants;

import lombok.Getter;
import util.MainSingleton;
import java.time.LocalTime;

@Getter
public class Plant {

    public static final String plantEmoji = "\uD83C\uDF31";
    private final LocalTime plantCreationTime = LocalTime.now();

    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    private double plantValue; // пока не используется





}
