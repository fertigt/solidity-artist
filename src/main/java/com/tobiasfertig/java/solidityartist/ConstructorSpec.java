package com.tobiasfertig.java.solidityartist;

public class ConstructorSpec implements Writable
{
	public final static String CONSTRUCTOR_KEYWORD = "constructor";

	private VisibilityName visibility;
	private CodeBlock code;

	private ConstructorSpec( Builder builder )
	{
		this.visibility = builder.visibility;
		this.code = (builder.code != null) ? builder.code : CodeBlock.emptyBlock( );
	}

	public void write( CodeWriter writer )
	{
		writer.writeAndIndent( CONSTRUCTOR_KEYWORD )
			  .emptyBraces( )
			  .space( )
			  .write( this.visibility )
			  .space( );

		if ( code.isEmpty( ) )
		{
			writer.emptyCurlyBraces( );
		} else
		{
			writer.openCurlyBraces( )
				  .write( code )
				  .closeCurlyBraces( );
		}
	}

	public static Builder builder( VisibilityName visibility )
	{
		return new Builder( visibility );
	}

	public static final class Builder
	{
		private final VisibilityName visibility;
		private CodeBlock code;

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
		}

		public Builder addCode( CodeBlock code )
		{
			this.code = code;
			return this;
		}

		public ConstructorSpec build( )
		{
			return new ConstructorSpec( this );
		}
	}
}
