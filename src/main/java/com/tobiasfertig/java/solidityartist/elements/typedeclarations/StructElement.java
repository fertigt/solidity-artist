package com.tobiasfertig.java.solidityartist.elements.typedeclarations;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class StructElement implements SolidityElement
{
	private final String name;
	private final Set<ParameterElement> structMembers;

	private StructElement( Builder builder )
	{
		this.name = builder.name;
		this.structMembers = builder.structMembers;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getName( )
	{
		return name;
	}

	public Set<ParameterElement> getStructMembers( )
	{
		return structMembers;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private final String name;
		private final Set<ParameterElement> structMembers = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addStructMember( ParameterElement structMember )
		{
			this.structMembers.add( structMember );
			return this;
		}

		public StructElement build( )
		{
			return new StructElement( this );
		}
	}
}
