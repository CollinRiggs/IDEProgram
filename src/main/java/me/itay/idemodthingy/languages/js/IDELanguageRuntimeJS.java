package me.itay.idemodthingy.languages.js;

import java.io.PrintStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.mrcrayfish.device.api.app.Application;

import me.itay.idemodthingy.api.IDELanguageRuntime;

public class IDELanguageRuntimeJS implements IDELanguageRuntime {
	
	private static final String BOOTSTRAP_CODE = "var print = function(obj) {\n" + 
			"	runtime.print(obj);\n" + 
			"}\n" + 
			"\n" + 
			"function Button(text, x, y, width, height) {\n" + 
			"	this.handle = runtime.createButton(text, x, y, width, height);\n" + 
			"	this.setClickListener = function(handler) {\n" + 
			"		runtime.button_setClickListener(this.handle, handler);\n" + 
			"	};\n" + 
			"}\n" + 
			"\n" + 
			"var app = {};\n" + 
			"\n" + 
			"app.addComponent = function(comp) {\n" + 
			"	runtime.addComponent(comp.handle);\n" + 
			"}\n" + 
			"\n" + 
			"app.message = function(text, title) {\n" + 
			"	if(!title) title = \"Message\";\n" + 
			"	runtime.message(text, title);\n" + 
			"}";
	
	@Override
	public String exe(Application app, PrintStream out, String code) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.put("runtime", new RuntimeFeatures(app, out));
			engine.eval(BOOTSTRAP_CODE);
			engine.eval(code);
			return null;
		} catch (Throwable e) {
			return e.getMessage().replaceAll("", "");
		}
	}
	
	
	
}