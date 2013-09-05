package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class ResponseUserAnswersConverter {

	public String buildResponseAnswerFromAreaAndColor(Area area, ColorModel color) {
		StringBuilder answerBuilder = new StringBuilder();
		answerBuilder.append(area.getX());
		answerBuilder.append(",");
		answerBuilder.append(area.getY());
		answerBuilder.append(" ");
		answerBuilder.append(color.toStringRgb());

		return answerBuilder.toString();
	}

	public Map<Area, ColorModel> convertResponseAnswersToAreaColorMap(List<String> currentAnswers) {
		Map<Area, ColorModel> areasWithColors = new HashMap<Area, ColorModel>();
		for (String currentAnswer : currentAnswers) {
			Area area = getAreaFromAnswer(currentAnswer);
			ColorModel color = getColorFromAnswer(currentAnswer);
			areasWithColors.put(area, color);
		}
		return areasWithColors;
	}

	public List<Area> convertResponseAnswersToAreaList(List<String> currentAnswers) {
		return Lists.transform(currentAnswers, new Function<String, Area>() {
			@Override
			public Area apply(String currentAnswer) {
				return  getAreaFromAnswer(currentAnswer);
			}
		});
	}

	private ColorModel getColorFromAnswer(String currentAnswer) {
		String[] splittedAnswer = currentAnswer.split(" ");
		String rbgString = splittedAnswer[1];
		ColorModel color = ColorModel.createFromRgbString(rbgString);
		return color;
	}

	private Area getAreaFromAnswer(String currentAnswer) {
		String[] splittedAnswer = currentAnswer.split(" ");
		String areaString = splittedAnswer[0];
		String[] splittedArea = areaString.split(",");

		int x = Integer.valueOf(splittedArea[0]);
		int y = Integer.valueOf(splittedArea[1]);
		return new Area(x, y);
	}
}
