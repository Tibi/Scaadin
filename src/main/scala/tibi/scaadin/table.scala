package tibi.scaadin

import com.vaadin.ui.Table.ColumnGenerator
import com.vaadin.data.Property
import com.vaadin.ui._

class STable[T](val columnInfos: Seq[ColumnInfo]) extends Table {

  setSizeFull()
  setSelectable(true)
  setImmediate(true)

  val infosById = columnInfos.map(ci ⇒ ci.id -> ci).toMap

  override def formatPropertyValue(rowId: Object, colId: Object,
    property: Property): String = {
    infosById.get(colId.toString) match {
      case Some(colInfo) => colInfo.formatter(property.getValue)
      case None => property.getValue.toString
    }
  }

  def setupColumns() {
    setVisibleColumns(columnInfos.map(_.id).toArray)
    for (colInfo ← columnInfos) {
      setColumnExpandRatio(colInfo.id, colInfo.widthRatio)
    }
    setColumnHeaders(columnInfos.map(_.header).toArray)
    setColumnAlignments(columnInfos.map(_.alignment).toArray)
  }

  def addSelectionListener(action: () => Unit) {
    addListener(new SPropertyListener(action))
  }

  def value: Option[T] = box(getValue.asInstanceOf[T])
}

case class ColumnInfo(id: String, header: String, widthRatio: Int,
  formatter: Any ⇒ String = _.toString,
  alignment: String = Table.ALIGN_LEFT
)

/**
 * Use it in a table to concatenate several columns into one.
 * Example: table.addGeneratedColumn("zip city", new ColumnConcatenator(List("ZIP_CODE", "CITY")))
 */
class ColumnConcatenator(columnIds: Seq[AnyRef], separator: String = " ") extends ColumnGenerator {
  def generateCell(source: Table, itemId: AnyRef, columnId: AnyRef): Component = {
    val item = source.getItem(itemId)
    // item.getI
    if (item == null) println("null item")
    // if (item.getItemProperty) println("null property")
    val list = for (colId ← columnIds) yield item.getItemProperty(colId).getValue match {
      case null ⇒ None
      case str: String ⇒ if (str.isEmpty) None else Some(str)
      case opt: Option[Any] ⇒ opt
      case other ⇒ Some(other)
    }
    new Label(removeOpt(list).mkString(separator))
  }

  def removeOpt[T](list: Seq[Option[T]]): Seq[T] = list.filter(!_.isEmpty).map(_.get)
}
