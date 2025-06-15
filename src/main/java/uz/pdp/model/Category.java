package uz.pdp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Categories")

public class Category extends BaseModel {
    public static final UUID PARENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "parentId")
    private UUID parentId;
}


//category  apple name categorytelefon(telefon category elektronika)

/**
 *
 * elektronika
 *  telefon
 *    apple
 *    sumsung
 *  quloqchin
 *    simli
 *    simsiz
 *  kamoyuter
 *   laptop
 *   pc
 *
 *
 * kiyim
 *
 *
 *
 * salomatlik
 *
 *
 *
 */
