package de.intranda.digiverso.model.queue;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
public class TicketType {
    @NonNull
    private String jobTypeName;

    @Getter
    @Setter
    @NonNull
    private String numberOfTickets;

    @Getter
    @Setter
    @NonNull
    private List<String> stepNames;


    public boolean isShowSearchButton() {
        return !numberOfTickets.equals("0");
    }

}
