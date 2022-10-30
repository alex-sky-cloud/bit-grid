package grid.bit.component.mapper;

import grid.bit.component.mapper.CycleAvoidingMappingContext;
import org.mapstruct.Context;

import java.util.List;

/** Данный интерфейс использует объект, который помогает избежать зацикливания
 * при обработки объектов имеющих ассоциативные ссылки на связанные объекты
 * * <p>Преобразователь описывает только выполнение конкретной операции
 *  *   Создан на замену трансформерам, чтобы избежать написания (объявления)
 *  *   методов трансформирования объектов, для каждой таблицы</p>
 * @param <D>  Указывает на универсальный тип данных,
 *             который будет подразумевать объект типа <b>dto</b>
 * @param <E> Указывает на универсальный тип данных,
 *  *  *  *     который будет подразумевать объект типа <b>entity</b>
 */
public interface CommonMapperForCycleAvoiding<D, E>  {

    /**
     * Преобразование объекта типа <b>entity</b>  в объект типа <b>dto</b>
     * @param e  объект-<b>entity</b
     * @param context  данный параметр нужен, чтобы избежать зацикливания
     *                во время преобразования связанных сущностей(сущности  из таблиц
     *                 между которому существуют отношения);
     *                   Тип этого параметра <b>CycleAvoidingMappingContext<b/>.
     *                   Это пользовательский
     *                 класс, который описывает обработку зацикливания и от кото
     * @return  возвращает объект типа <b>dto</b>
     */
    D toDto(E e, @Context CycleAvoidingMappingContext context);

    /**
     * Преобразование объекта типа   <b>dto</b> в объект типа <b>entity</b>
     * @param d - объект-<b>dto</b>
     *@param context  данный параметр нужен, чтобы избежать зацикливания
     *                     во время преобразования связанных сущностей(сущности  из таблиц
     *                      между которому существуют отношения);
     *                     класс, который описывает обработку зацикливания и от кото
     * @return - возвращает объект типа <b>entity</b
     */
    E toEntity(D d, @Context CycleAvoidingMappingContext context);


    /**
     * Преобразование списка объектов типа <b>entity</b> в список объектов типа <b>dto</b>
     * @param entityList список объектов-<b>entity</b
     * @return возвращает список объектов типа <b>dto</b>
     */
    List<D> toListDto(List<E> entityList, @Context CycleAvoidingMappingContext context);

    /**
     * Преобразование списка объектов типа <b>dto</b> в список объектов типа <b>entity</b>
     * @param dtoList список объектов-<b>dto</b
     * @return возвращает список объектов типа <b>entity</b>
     */
    List<E> toListEntity(List<D> dtoList, @Context CycleAvoidingMappingContext context);

}
