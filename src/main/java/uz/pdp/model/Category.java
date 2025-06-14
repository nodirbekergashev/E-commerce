package uz.pdp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import uz.pdp.baseAbstractions.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@JacksonXmlRootElement(localName = "Category")

public class Category extends BaseModel {
    private String name;//apple//telefon
    private UUID parentId;//telefon//elektronika
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
