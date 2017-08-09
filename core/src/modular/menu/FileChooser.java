package modular.menu;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author olli-pekka
 *	@version 1.0
 * File Chooser taken from http://www.java-gaming.org/index.php?topic=35471.0
 * 
 * LibGdx does not support JFileChooser and implementing own file chooser is outside the scope of the assignment.
 */
public class FileChooser extends Dialog{
	/**
	 * Skin of FileChooser.
	 */
	private Skin skin;
	/**
	 * Default FileChooser directory.
	 */
	private FileHandle directory;
	/**
	 * The file to be opened.
	 */
	private FileHandle file;
	   
	   /**
	 * @return the current file handle.
	 */
	public FileHandle getFile() {
	      return file;
	   }
	   
	   /**
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Dialog#show(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	   public Dialog show(Stage stage) {
	      return super.show(stage);
	   }

	   /**
	 * @param directory is the new directory to set as the default.
	 */
	public void setDirectory(FileHandle directory) {
	      if (this.directory != directory) {
	         this.directory = directory;
	         this.file = null;
	         buildList();
	      }
	   }
	   
	   /**
	 * @param file is the new file to set this file handler to.
	 */
	public void setFile(FileHandle file) {
	      if (this.file != file) {
	         if (this.file != null) {
	            Label label = (Label) this.findActor(this.file.name());
	            label.setColor(Color.WHITE);
	         }
	         Label label = (Label) this.findActor(file.name());
	         label.setColor(Color.RED);
	         this.file = file;
	      }      
	   }
	   
	   /**
	 * @param title Name of this file chooser
	 * @param skin Skin of this file chooser
	 */
	public FileChooser(String title, Skin skin) {
	      super(title, skin);
	      this.getCell(getButtonTable()).expandX().fill();
	      this.getButtonTable().defaults().expandX().fill();
	      
	      this.button("Cancel", "Cancel");
	      this.button("OK", "OK");
	   
	      this.setModal(true);
	      this.skin = skin;
	   }

	   /**
	 * @param title 
	 * @param skin
	 * @param windowStyleName style of the skin
	 */
	public FileChooser(String title, Skin skin, String windowStyleName) {
	      super(title, skin, windowStyleName);
	      this.setModal(true);
	      this.skin = skin;
	   }
	   
	   /**
	 * Creates the file chooser interface.
	 */
	private void buildList() {
	      FileHandle[] files = directory.list();
	      Arrays.sort(files, new Comparator<FileHandle>() {
	         @Override
	         public int compare(FileHandle o1, FileHandle o2) {
	            if (o1.isDirectory() && !o2.isDirectory()) {
	               return -1;
	            }
	            if (o2.isDirectory() && !o1.isDirectory()) {
	               return +1;
	            }
	            return o1.name().compareToIgnoreCase(o2.name());
	         }
	      });
	      ScrollPane pane = new ScrollPane(null, skin);
	      pane.setWidth(500);
	      pane.setHeight(500);
	      Table table = new Table().top().left();
	      table.defaults().left();
	      ClickListener fileClickListener = new ClickListener(){
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	            Label target = (Label) event.getTarget();
	            if (target.getName().equals("..")) {
	               setDirectory(directory.parent());
	            } else {
	               FileHandle handle = directory.child(target.getName());
	               if (handle.isDirectory()) {
	                  setDirectory(handle);
	               } else {
	                  setFile(handle);
	               }
	            }            
	         }
	      };
	      table.row();
	      Label label = new Label("..", skin);
	      label.setName("..");
	      label.addListener(fileClickListener);
	      table.add(label).maxWidth(500).maxHeight(500).expandX().fillX();
	      for (FileHandle file : files) {
	         table.row();
	         label = new Label(file.name(), skin);
	         label.setName(file.name());
	         label.addListener(fileClickListener);
	         table.add(label).maxWidth(500).maxHeight(500).expandX().fillX();
	      }
	      this.setResizable(true);
	      this.setSize(500, 500);
	      pane.setWidget(table);
	      this.getContentTable().reset();
	      this.getContentTable().add(pane).maxHeight(500).expand().fill();
	   }
	   
}
