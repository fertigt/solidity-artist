package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeElement implements SolidityElement
{
	private final List<String> lines;

	private CodeElement( Builder builder )
	{
		this.lines = builder.lines;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public List<String> getLines( )
	{
		return lines;
	}

	public static Builder builder( )
	{
		return new Builder( );
	}

	public static final class Builder
	{
		private final List<String> lines = new ArrayList<>( );

		private Builder( )
		{

		}

		public Builder addStatement( String line )
		{
			this.lines.add( line + ";" );
			return this;
		}

		public Builder addCode( String code )
		{
			String[] lines = code.split( "\n" );
			this.lines.addAll( Arrays.asList( lines ) );
			return this;
		}

		public Builder addCodeElement( CodeElement codeElement )
		{
			this.lines.addAll( codeElement.getLines( ) );
			return this;
		}

		public CodeElement build( )
		{
			return new CodeElement( this );
		}
	}
}
