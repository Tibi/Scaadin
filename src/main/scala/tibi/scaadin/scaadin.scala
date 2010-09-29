package tibi.scaadin

import collection.JavaConversions._
import com.vaadin.ui._
import com.vaadin.data._
import com.vaadin.ui.Table.ColumnGenerator

  
class SButtonClickListener(action: () => Unit) extends Button.ClickListener {
  def buttonClick(event: Button#ClickEvent): Unit = {
    action()
  }
}

class SPropertyListener(action: () => Unit) extends Property.ValueChangeListener {
  def valueChange(event: Property.ValueChangeEvent) {
    action()
  }
}

/**
 * A Button to which the on-click action can be passed in the constructor.
 */
class SButton(text: String, action: () => Unit) extends Button(text, new SButtonClickListener(action))

class SWindowCloseListener(action: Window#CloseEvent => Unit) extends Window.CloseListener {
  def windowClose(event: Window#CloseEvent) = {
    action(event)
  }
}

// allows doing: component.setWidth(7 pixels)
class Dimension(private val value: Number) {
  def pixels: String = value + "px"
  def percent: String = value + "%"
}
object Dimension {
  implicit def intToDimension(value: Int): Dimension = new Dimension(value)
  implicit def doubleToDimension(value: Double): Dimension = new Dimension(value)
}

/**
 * A ComboBox that knows the type of its items.
 */
class SCombo[T](text: String, items: Seq[T] = Nil) extends NativeSelect(text, items) {
  
  def value: Option[T] = getValue match {
    case null => None
    case some => Some(some.asInstanceOf[T])
  }
}

class STextField(title: String) extends TextField(title) {
  def value = getValue.asInstanceOf[String]
}
class SLongField(title: String) extends TextField(title) {
  def value = toLong(getValue)
}
class SIntField(title: String) extends TextField(title) {
  def value = toInt(getValue)
}

class SCheckBox(title: String) extends CheckBox(title) {
  def isChecked = getValue.asInstanceOf[Boolean]
  def value = isChecked
}
