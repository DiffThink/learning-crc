package ptsi.service.centers.control;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

import ptsi.service.centers.entity.TrainingCenterEntity;

public class TrainingCenterSerializer implements JsonbSerializer<TrainingCenterEntity>{

	@Override
	public void serialize(TrainingCenterEntity obj, JsonGenerator generator, SerializationContext ctx) {
		generator.writeStartObject();
		generator.write("program_code", obj.getProgramCode());
		//generator.write("program_name", obj.getProgramName());
		generator.writeEnd();
	}

}
