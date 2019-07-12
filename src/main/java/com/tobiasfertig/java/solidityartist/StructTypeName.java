package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StructTypeName extends TypeName
{
	private String name;
	private List<ParameterSpec> structMembers;

	public StructTypeName( String name, List<ParameterSpec> structMembers )
	{
		super( Keywords.STRUCT.toString( ) );
		this.name = name;
		this.structMembers = structMembers;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.indent( );
		super.write( writer );
		writer.space( )
			  .write( this.name )
			  .space( )
			  .openCurlyBraces( );

		Iterator<ParameterSpec> iterator = structMembers.iterator( );
		if ( iterator.hasNext( ) )
		{
			ParameterSpec nextStructMember = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				writer.indent( )
					  .write( nextStructMember )
					  .semicolonAndNewline( );

				nextStructMember = iterator.next( );
			}

			writer.indent( )
				  .write( nextStructMember )
				  .semicolon( );
		}

		writer.closeCurlyBraces( );
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public final static class Builder
	{
		private final List<ParameterSpec> structMembers = new ArrayList<>( );
		private final String name;

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addStructMember( ParameterSpec structMember )
		{
			this.structMembers.add( structMember );
			return this;
		}

		public StructTypeName build( )
		{
			return new StructTypeName( name, structMembers );
		}
	}
}
