package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;

public class ErrorsToCountModeAdjuster {

	public int adjustValueToCountMode(int value, CountMode countMode){
		int adjustedValue;
		if(countMode == CountMode.CORRECT_ANSWERS){
			adjustedValue = value;
		}else{
			adjustedValue = getOneIfBiggerThenZero(value);
		}
		
		return adjustedValue;
	}
	
	private int getOneIfBiggerThenZero(int value){
		if(value > 0){
			return 1;
		}else{
			return value;
		}
	}
}
