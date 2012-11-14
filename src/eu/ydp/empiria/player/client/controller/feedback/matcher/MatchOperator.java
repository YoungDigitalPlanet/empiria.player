package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.common.collect.ComparisonChain;

public enum MatchOperator {
	
	EQUAL("=="){
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) == 0;
		}
	}, 
	NOT_EQUAL("!=") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) != 0;
		}
	},
	LESS("<") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) < 0;
		}
	},
	LESS_EQUAL("<=") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) <= 0;
		}
	},
	GREATER(">") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) > 0;
		}
	},
	GREATER_EQUAL(">=") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return compare(val1, val2) >= 0;
		}
	},
	NONE("") {
		@Override
		public boolean match(Comparable<?> val1, Comparable<?> val2) {
			return false;
		}
	};
	
	private String operator;
	
	private MatchOperator(String operator) {
		this.operator = operator;
	}
	
	public abstract boolean match(Comparable<?> val1, Comparable<?> val2);
	
	protected int compare(Comparable<?> val1, Comparable<?> val2) {
		return ComparisonChain.start().compare(val1, val2).result();
	}
	
	public static MatchOperator getOperator(String value){
		MatchOperator searchedOperator = NONE;

		for (MatchOperator matchOperator : values()) {
			if (matchOperator.operator.equals(value)) {
				searchedOperator = matchOperator;
				break;
			}
		}
		
		return searchedOperator;
	}
	
	public String getOperator() {
		return operator;
	}
}
