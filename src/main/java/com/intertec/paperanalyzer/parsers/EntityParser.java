package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.domainmodels.Entity;
import com.intertec.paperanalyzer.commons.FileReader;
import com.intertec.paperanalyzer.factories.EntityFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityParser implements LinesFileParser<Map<Integer, Entity>> {
    @Override
    public Map<Integer, Entity> parseLines(InputStream is) throws IOException {
        return FileReader.getLinesFromResource(is)
                .parallelStream()
                .map(EntityFactory::parseEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Entity::getNodeId, entity -> entity, (entity1, entity2) -> entity1));
    }
}
