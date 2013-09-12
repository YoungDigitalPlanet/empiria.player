package eu.ydp.empiria.player.client.module.drawing.toolbox;

import static eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType.ERASER;
import static eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType.PENCIL;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ToolboxPresenter {
	@Inject @ModuleScoped private ToolboxView view;
	@Inject @ModuleScoped private ToolboxButtonCreator buttonCreator;
	@Inject private ToolboxModelImpl model;
	@Inject private ToolFactory toolFactory;
	@Inject @ModuleScoped private CanvasPresenter canvasPresenter;
	@Inject private DrawCommandFactory drawCommandFactory;
	@Inject private PaletteColorsProvider paletteColorsProvider;
	
	private boolean paletteVisible;

	public void init(){
		initView();
		initTool();
		initPalette();
	}

	private void initView() {
		view.setPresenterAndBind(this);
		buttonCreator.setPresenter(this);
	}

	private void initPalette() {
		List<ColorModel> colorModels = paletteColorsProvider.getColors();
		view.setPalette(colorModels);
		selectColor(colorModels.get(0));
	}

	private void initTool() {
		pencilClicked();
	}
	 
	public void colorClicked(ColorModel colorModel){
		paletteClicked();
		selectColor(colorModel);
	}

	private void selectColor(ColorModel colorModel) {
		view.setPaletteColor(colorModel);
		model.setColorModel(colorModel);
		update();
	}

	public void paletteClicked(){
		if (paletteVisible){
			view.hidePalette();
		} else {
			view.showPalette();
		}
		paletteVisible = !paletteVisible;
	}
	
	public void pencilClicked(){
		view.selectPencil();
		model.setToolType(PENCIL);
		update();
	}
	
	public void eraserClicked(){
		view.selectEraser();
		model.setToolType(ERASER);
		update();
	}
	
	public void clearAllClicked(){
		DrawCommand command = drawCommandFactory.createCommand(DrawCommandType.CLEAR_ALL);
		command.execute();
	}
	
	private void update() {
		Tool tool = toolFactory.createTool(model);
		canvasPresenter.setTool(tool);
	}

	public ToolboxView getView() {
		return view;
	}
}
