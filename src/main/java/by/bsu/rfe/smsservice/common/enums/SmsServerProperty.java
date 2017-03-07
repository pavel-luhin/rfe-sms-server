package by.bsu.rfe.smsservice.common.enums;

/**
 * Created by pluhin on 1/4/17.
 */
public enum SmsServerProperty {
    MUTE_ENABLED(SmsServerPropertyGroup.MUTE_MODE),
    MUTE_START_TIME(SmsServerPropertyGroup.MUTE_MODE),
    MUTE_END_TIME(SmsServerPropertyGroup.MUTE_MODE);

    private SmsServerPropertyGroup propertyGroup;

    SmsServerProperty(SmsServerPropertyGroup propertyGroup) {
        this.propertyGroup = propertyGroup;
    }

    public SmsServerPropertyGroup getPropertyGroup() {
        return propertyGroup;
    }

    public enum SmsServerPropertyGroup {
        MUTE_MODE, UNASSIGNED;
    }
}
