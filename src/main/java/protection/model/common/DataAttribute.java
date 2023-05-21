package protection.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
public class DataAttribute<T> extends DATA {

    @Getter @Setter
    private T value;
}
