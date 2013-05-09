package eu.ydp.empiria.player.client.controller.variables.objects;

public enum CheckMode {
	DEFAULT, EXPRESSION;

    @Override
    public String toString() {
          return super.toString().toLowerCase();
    };

    public static CheckMode fromString(String value){
    	if("expression".equalsIgnoreCase(value)){
    		return EXPRESSION;
    	}else{
    		return DEFAULT;
    	}
    }
}
