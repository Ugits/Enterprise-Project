package org.jonas.enterpriseproject.spell.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.List;

public record SpellDTO(

        @NotNull(message = "Index cannot be null.")
        @NotBlank(message = "Index cannot be blank.")
        @Size(min = 1, max = 50, message = "Index must be between 1 and 50 characters.")
        String index,

        @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name cannot be blank.")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters.")
        String name,

        @NotNull(message = "Level cannot be null.")
        @Min(value = 0, message = "Level must be at least 0.")
        @Max(value = 9, message = "Level must be at most 9.")
        Integer level,

        List<CharClassDTO> classes,

        List<SpellDescriptionDTO> description,

        @NotNull(message = "Casting time cannot be null.")
        @NotBlank(message = "Casting time cannot be blank.")
        @Size(min = 1, max = 50, message = "Casting time must be between 1 and 50 characters.")
        @JsonProperty("casting_time")
        String castingTime,

        @NotNull(message = "Range cannot be null.")
        @NotBlank(message = "Range cannot be blank.")
        @Size(min = 1, max = 50, message = "Range must be between 1 and 50 characters.")
        String range,

        @NotNull(message = "Duration cannot be null.")
        @NotBlank(message = "Duration cannot be blank.")
        @Size(min = 1, max = 50, message = "Duration must be between 1 and 50 characters.")
        String duration,

        @NotNull(message = "Ritual must be specified.")
        Boolean ritual,

        @NotNull(message = "Concentration must be specified.")
        Boolean concentration
) {
        // factory method for name
        public static SpellDTO createWithIndex(String index) {
                return new SpellDTO(index, "temp", 0,List.of(), List.of(), "temp", "temp", "temp", false, false);
        }

        // factory method for name
        public static SpellDTO createWithName(String name) {
                return new SpellDTO("temp", name, 0,List.of(), List.of(), "temp", "temp", "temp", false, false);
        }

        // factory for level only
        public static SpellDTO createWithLevel(Integer level) {
                return new SpellDTO("temp", "temp", level,List.of(), List.of(), "temp", "temp", "temp", false, false);
        }

        // factory for ritual and level
        public static SpellDTO createWithRitualAndLevel(Boolean ritual, Integer maxLevel) {
                return new SpellDTO("temp", "temp", maxLevel,List.of(), List.of(), "temp", "temp", "temp", ritual, false);
        }

        // factory for casting time and level
        public static SpellDTO createWithCastingTimeAndLevel(String castingTime, Integer maxLevel) {
                return new SpellDTO("temp", "temp", maxLevel,List.of(), List.of(), castingTime, "temp", "temp", false, false);
        }

        // factory for concentration and level
        public static SpellDTO createWithConcentrationAndLevel(Boolean concentration, Integer maxLevel) {
                return new SpellDTO("temp", "temp", maxLevel,List.of(), List.of(), "temp", "temp", "temp", false, concentration);
        }

        // factory for duration and level
        public static SpellDTO createWithDurationAndLevel(String duration, Integer maxLevel) {
                return new SpellDTO("temp", "temp", maxLevel,List.of(), List.of(), "temp", "temp", duration, false, false);
        }

        // factory for range and level
        public static SpellDTO createWithRangeAndLevel(String range, Integer maxLevel) {
                return new SpellDTO("temp","temp", maxLevel,List.of(), List.of(), "temp", range, "temp", false, false);
        }



}
