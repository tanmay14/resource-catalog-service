package org.tmf.openapi.resource.domain.common;

import java.util.EnumSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LifeCycleStatus {

	IN_STUDY("In Study"), IN_DESIGN("In Design"), IN_TEST("In Test"), ACTIVE("Active"), LAUNCHED("Launched"), RETIRED(
			"Retired"), OBSOLETE("Obsolete"), REJECTED("Rejected");

	private String value;

	private LifeCycleStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@JsonValue(true)
	public String getValue() {
		return this.value;
	}

	public boolean canTransitionFrom(LifeCycleStatus currentStatus) {
		if (this == currentStatus) {
			return true;
		}

		switch (this) {
		case IN_STUDY: {
			if (currentStatus == null) {
				return true;
			}

			return false;
		}

		case IN_DESIGN: {
			if (currentStatus == null || currentStatus == IN_STUDY || currentStatus == IN_TEST) {
				return true;
			}

			return false;
		}

		case IN_TEST: {
			if (currentStatus == null || currentStatus == IN_DESIGN) {
				return true;
			}

			return false;
		}

		case ACTIVE: {
			if (currentStatus == null || currentStatus == IN_TEST) {
				return true;
			}

			return false;
		}

		case LAUNCHED: {
			if (currentStatus == null || currentStatus == ACTIVE) {
				return true;
			}

			return false;
		}

		case RETIRED: {
			if (currentStatus == ACTIVE || currentStatus == LAUNCHED) {
				return true;
			}

			return false;
		}

		case OBSOLETE: {
			if (currentStatus == RETIRED) {
				return true;
			}

			return false;
		}

		case REJECTED: {
			if (currentStatus == IN_TEST) {
				return true;
			}

			return false;
		}

		default: {
			return false;
		}
		}
	}

	public static Set<LifeCycleStatus> transitionableStatues(LifeCycleStatus lifecycleStatus) {

		if (lifecycleStatus == null) {
			return EnumSet.of(LifeCycleStatus.IN_STUDY, LifeCycleStatus.IN_DESIGN, LifeCycleStatus.IN_TEST,
					LifeCycleStatus.ACTIVE, LifeCycleStatus.LAUNCHED);
		}

		switch (lifecycleStatus) {
		case IN_STUDY: {
			return EnumSet.of(LifeCycleStatus.IN_DESIGN);
		}

		case IN_DESIGN: {
			return EnumSet.of(LifeCycleStatus.IN_TEST);
		}

		case IN_TEST: {
			return EnumSet.of(LifeCycleStatus.IN_DESIGN, LifeCycleStatus.ACTIVE, LifeCycleStatus.REJECTED);
		}

		case ACTIVE: {
			return EnumSet.of(LifeCycleStatus.LAUNCHED, LifeCycleStatus.RETIRED);
		}

		case LAUNCHED: {
			return EnumSet.of(LifeCycleStatus.RETIRED);
		}

		case RETIRED: {
			return EnumSet.of(LifeCycleStatus.OBSOLETE);
		}

		case OBSOLETE: {
			return null;
		}

		case REJECTED: {
			return null;
		}

		default: {
			return EnumSet.noneOf(LifeCycleStatus.class);
		}
		}
	}

	public static LifeCycleStatus find(String value) {
		for (LifeCycleStatus lifecycleStatus : values()) {
			if (lifecycleStatus.value.equals(value)) {
				return lifecycleStatus;
			}
		}

		return null;
	}

}
