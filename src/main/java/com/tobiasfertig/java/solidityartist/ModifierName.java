package com.tobiasfertig.java.solidityartist;

public class ModifierName implements Writable
{
	public static final ModifierName PAYABLE = new ModifierName( "payable" );
	public static final ModifierName VIEW = new ModifierName( "view" );
	public static final ModifierName PURE = new ModifierName( "pure" );
	public static final ModifierName CONSTANT = new ModifierName( "constant" );

	private final String keyword;

	//TODO: sorted set -> allowed combinations
	public ModifierName( String keyword )
	{
		this.keyword = keyword;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( this.keyword );
	}
}
