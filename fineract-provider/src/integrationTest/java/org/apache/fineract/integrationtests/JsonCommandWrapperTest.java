package org.apache.fineract.integrationtests;

import org.apache.fineract.infrastructure.core.api.JsonCommand;

public class JsonCommandWrapperTest {
    private JsonCommand command;

    public JsonCommand getCommand() {
        return command;
    }

    public void setCommand(JsonCommand command) {
        this.command = command;
    }
}
