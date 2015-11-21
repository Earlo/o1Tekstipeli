package o1.adventure.ui


import swing._
import scala.swing.BorderPanel.Position
import scala.swing.event._
import scala.swing.GridBagPanel._
import javax.swing.UIManager
import o1.adventure.Adventure
import world.World
import o1.adventure.NPC



/**
 * @author pollarv1
 */
class ChatWindow( target:NPC, text:String = "" ) extends Dialog {

  title = "Chat with " + target
  modal = true


  
  contents = new BorderPanel {
    
    val chatLog = new TextArea(7, 20) {
      editable = false
      wordWrap = true
      lineWrap = true
      }
    chatLog.text = text
    
    layout(this.chatLog) = scala.swing.BorderPanel.Position.North
    
    this.updateButtons()  
    
    def updateButtons(){
      val buttonGrid = new GridPanel(1,2){
        for ( option <- target.chatOptions() ){    
           val b = new Button( option.capitalize ) {}
           this.contents += b
           listenTo(b)
           reactions += {
             case ButtonClicked(component) if component == b =>
               chatLog.text = World.player.chat.getClass.getMethod( option ).invoke( World.player.chat ).toString() + target.chat.getClass.getMethod( option ).invoke( target.chat ).toString()               
               if ( target.chat.checkChange() ){
                 close()
                 AdventureGUI.StartChat( target, text = chatLog.text )
               }
           }
        }    
      }
      layout( buttonGrid ) = scala.swing.BorderPanel.Position.South
    }
  }

  centerOnScreen()
  open()
}
