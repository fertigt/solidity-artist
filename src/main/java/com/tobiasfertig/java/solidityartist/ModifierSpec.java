package com.tobiasfertig.java.solidityartist;

public class ModifierSpec implements Writable
{
	private ModifierName modifierName;

	public ModifierSpec( ModifierName modifierName )
	{
		this.modifierName = modifierName;
	}

	@Override public void write( CodeWriter writer )
	{

	}

	public ModifierName getModifierName( )
	{
		return this.modifierName;
	}
}
