package org.master.chatservice.dto;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SignalMessage{
        private String target;
        private String from;
        private JsonNode signal;

}
