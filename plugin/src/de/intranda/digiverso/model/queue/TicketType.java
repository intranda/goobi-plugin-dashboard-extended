package de.intranda.digiverso.model.queue;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketType {
    @NonNull
    private String ticketName;
    private int numberOfTickets = 0;
    private List<Integer> processIds = new ArrayList<>();

    public void addProcessId(Integer id) {
        processIds.add(id);
    }

    public int getNumberOfTickets() {
        if (numberOfTickets == 0) {
            return processIds.size();
        }
        return numberOfTickets;
    }

    public boolean isShowSearchButton() {
        return !processIds.isEmpty();
    }

}
