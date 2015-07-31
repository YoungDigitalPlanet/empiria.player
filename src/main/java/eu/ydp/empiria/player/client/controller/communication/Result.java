package eu.ydp.empiria.player.client.controller.communication;

public class Result {

    private float min;
    private float max;
    private float score;

    public Result() {
        min = 0;
        max = 1;
        score = 0;
    }

    public Result(float outcomeScore, float lowerBound, float upperBound) {
        this();
        min = lowerBound;
        max = upperBound;
        score = outcomeScore;

    }

    public Result(Float outcomeScore, Float lowerBound, Float upperBound) {
        this();
        if (lowerBound != null)
            min = lowerBound;

        if (upperBound != null)
            max = upperBound;

        if (outcomeScore != null)
            score = outcomeScore;

    }

    public float getScore() {
        return score;
    }

    public float getMinPoints() {
        return min;
    }

    public float getMaxPoints() {
        return max;
    }

    public void merge(Result result) {
        score += result.getScore();
        min += result.getMinPoints();
        max += result.getMaxPoints();
    }
}
