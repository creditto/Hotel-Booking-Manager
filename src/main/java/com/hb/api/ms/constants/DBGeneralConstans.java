package com.hb.api.ms.constants;

import java.util.EnumSet;
import java.util.HashMap;

public enum DBGeneralConstans {

	SUCCESSFUL("Room reservation completed successfully"), FAILED("Room reservation failed"),
	ENTRY_ALREADY_EXITS("Room already booked for given date"), NOT_EXITS("Room not available for given date");

	private String constants;

	private static final HashMap<String, DBGeneralConstans> constantsMap = new HashMap<String, DBGeneralConstans>();

	static {
		for (DBGeneralConstans dbconstants : EnumSet.allOf(DBGeneralConstans.class)) {
			constantsMap.put(dbconstants.getName(), dbconstants);
		}
	}

	public static DBGeneralConstans get(String constants) {
		return constantsMap.get(constants);
	}

	private DBGeneralConstans(String constants) {
		this.constants = constants;
	}

	public short shortValue() {
		return (short) ordinal();
	}

	public String getName() {
		return constants;
	}
}
