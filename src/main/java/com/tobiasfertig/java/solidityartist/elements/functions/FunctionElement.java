package com.tobiasfertig.java.solidityartist.elements.functions;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class FunctionElement implements SolidityElement
{
	private final NatSpecElement comment;
	private final boolean isFallback;
	private final String name;
	private final Set<ParameterElement> parameters;
	private final Keyword.Visibility visibility;
	private final Set<Keyword.Modifier> modifiers;
	private final Set<String> customModifiers;
	private final List<ParameterElement> returnParameters;
	private final boolean isAbstract;
	private final CodeElement code;

	private FunctionElement( Builder builder )
	{
		this.comment = builder.comment;
		this.isFallback = builder.isFallback;
		this.name = builder.name;
		this.parameters = builder.parameters;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
		this.returnParameters = builder.returnParameters;
		this.isAbstract = builder.isAbstract;
		this.code = builder.code;
	}

	@Override
	public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	@Override public boolean equals( Object obj )
	{
		boolean result;

		if ( this == obj )
		{
			result = true;
		}
		else if (obj == null )
		{
			result = false;
		}
		else if ( this.getClass( ) != obj.getClass( ) )
		{
			result = false;
		}
		else
		{
			FunctionElement function = ( FunctionElement ) obj;
			result = this.getFunctionSelector( ).equals( function.getFunctionSelector( ) );
		}

		return result;
	}

	@Override public int hashCode( )
	{
		return getFunctionSelector( ).hashCode( );
	}

	private String getFunctionSelector( )
	{
		StringBuilder sb = new StringBuilder( );
		sb.append( name );
		sb.append( "(" );

		Iterator<ParameterElement> iterator = parameters.iterator( );

		if ( iterator.hasNext( ) )
		{
			ParameterElement parameter = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				appendParameterToFunctionSelector( parameter, sb );
				sb.append( "," );

				parameter = iterator.next( );
			}

			appendParameterToFunctionSelector( parameter, sb );
		}

		sb.append( ")" );

		byte[] digest = calculateFunctionSelector( sb.toString( ) );
		return Hex.toHexString( Arrays.copyOfRange( digest, 0, 4 ) );
	}

	private void appendParameterToFunctionSelector( ParameterElement parameter, StringBuilder sb )
	{
		switch ( parameter.getDataType( ).getTypeName( ) )
		{
		case "uint":
			sb.append( "uint256" );
			break;
		case "int":
			sb.append( "int256" );
			break;
		case "ufixed":
			sb.append( "ufixed128x18" );
			break;
		case "fixed":
			sb.append( "fixed128x18" );
			break;
		case "address payable":
			sb.append( "address" );
			break;
		case "byte":
			sb.append( "bytes1" );
			break;
		default:
			sb.append( parameter.getDataType( ) );
		}
	}

	private byte[] calculateFunctionSelector( String functionSignature )
	{
		Keccak.Digest256 digest = new Keccak.Digest256( );
		digest.update( functionSignature.getBytes( StandardCharsets.UTF_8 ) );
		return digest.digest( );
	}

	public NatSpecElement getComment( )
	{
		return comment;
	}

	public boolean isFallback( )
	{
		return isFallback;
	}

	public String getName( )
	{
		return name;
	}

	public Set<ParameterElement> getParameters( )
	{
		return parameters;
	}

	public Keyword.Visibility getVisibility( )
	{
		return visibility;
	}

	public Set<Keyword.Modifier> getModifiers( )
	{
		return modifiers;
	}

	public Set<String> getCustomModifiers( )
	{
		return customModifiers;
	}

	public List<ParameterElement> getReturnParameters( )
	{
		return returnParameters;
	}

	public boolean isAbstract( )
	{
		return isAbstract;
	}

	public CodeElement getCode( )
	{
		return code;
	}

	public static Builder externalBuilder( String name )
	{
		return new Builder( Keyword.Visibility.EXTERNAL, false, name );
	}

	public static Builder internalBuilder( String name )
	{
		return new Builder( Keyword.Visibility.INTERNAL, false, name );
	}

	public static Builder privateBuilder( String name )
	{
		return new Builder( Keyword.Visibility.PRIVATE, false, name );
	}

	public static Builder publicBuilder( String name )
	{
		return new Builder( Keyword.Visibility.PUBLIC, false, name );
	}

	public static Builder fallbackBuilder( )
	{
		return new Builder( Keyword.Visibility.EXTERNAL, true, "" );
	}

	public static final class Builder
	{
		private NatSpecElement comment;
		private final boolean isFallback;
		private final String name;
		private final Set<ParameterElement> parameters = new LinkedHashSet<>( );
		private final Keyword.Visibility visibility;
		private final Set<Keyword.Modifier> modifiers = new LinkedHashSet<>( );
		private final Set<String> customModifiers = new LinkedHashSet<>( );
		private final List<ParameterElement> returnParameters = new ArrayList<>( );
		private boolean isAbstract;
		private CodeElement code;

		private Builder( Keyword.Visibility visibility, boolean isFallback, String name )
		{
			this.visibility = visibility;
			this.isFallback = isFallback;
			this.name = name;
		}

		public Builder addNatSpec( NatSpecElement comment )
		{
			this.comment = comment;
			return this;
		}

		public Builder addParameters( Iterable<ParameterElement> parameterSpecs )
		{
			for ( ParameterElement parameterSpec : parameterSpecs )
			{
				this.parameters.add( parameterSpec );
			}

			return this;
		}

		public Builder addParameter( ParameterElement parameterSpec )
		{
			this.parameters.add( parameterSpec );
			return this;
		}

		public Builder isPure( )
		{
			this.modifiers.add( Keyword.Modifier.PURE );
			return this;
		}

		public Builder isView( )
		{
			this.modifiers.add( Keyword.Modifier.VIEW );
			return this;
		}

		public Builder isPayable( )
		{
			this.modifiers.add( Keyword.Modifier.PAYABLE );
			return this;
		}

		public Builder isAbstract( )
		{
			this.isAbstract = true;
			return this;
		}

		public Builder addCustomModifierElements( Iterable<ModifierElement> customModifiers )
		{
			for ( ModifierElement customModifier : customModifiers )
			{
				this.customModifiers.add( customModifier.getName( ) );
			}

			return this;
		}

		public Builder addCustomModifiers( Iterable<String> customModifiers )
		{
			for ( String customModifier : customModifiers )
			{
				this.customModifiers.add( customModifier );
			}

			return this;
		}

		public Builder addCustomModifier( ModifierElement customModifier )
		{
			this.customModifiers.add( customModifier.getName( ) );
			return this;
		}

		public Builder addCustomModifierName( String customModifier )
		{
			this.customModifiers.add( customModifier );
			return this;
		}

		public Builder addReturnParameters( Iterable<ParameterElement> returnParameters )
		{
			for ( ParameterElement returnParameter : returnParameters )
			{
				this.returnParameters.add( returnParameter );
			}

			return this;
		}

		public Builder addReturnParameter( ParameterElement returnParameter )
		{
			this.returnParameters.add( returnParameter );
			return this;
		}

		public Builder addCode( CodeElement code )
		{
			this.code = code;
			return this;
		}

		public FunctionElement build( )
		{
			return new FunctionElement( this );
		}
	}
}
