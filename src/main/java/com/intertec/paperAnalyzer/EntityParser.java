package com.intertec.paperAnalyzer;

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
