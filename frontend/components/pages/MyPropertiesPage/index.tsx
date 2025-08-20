import MainLayout from '@/components/layout/MainLayout';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { MapPin, Bed, Bath, Home, Edit, TrendingUp, Eye } from 'lucide-react';
import Link from 'next/link';

// Mock data - em uma aplicação real, isso viria de uma API
interface IMyPropertiesProps {
    properties: IProperty[];
}

interface IProperty {
    id: string,
    title: string,
    location: string,
    price: number,
    image: string,
    type: string,
    bedrooms: number,
    bathrooms: number,
    area: number,
    status: string,
    views: number,
    proposals: number;
}

const MyPropertiesPage: React.FC<IMyPropertiesProps> = ({ properties }) => {
    return (
        <MainLayout>
            <div className="container mx-auto px-4 py-8">
                <div className="flex justify-between items-center mb-8">
                    <div>
                        <h1 className="text-3xl font-bold text-gray-900">Minhas Propriedades</h1>
                        <p className="text-gray-600 mt-2">Gerencie suas propriedades anunciadas</p>
                    </div>
                    <Link href="/add-property">
                        <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                            Adicionar Propriedade
                        </Button>
                    </Link>
                </div>

                <div className="grid gap-6">
                    {properties.map((property) => (
                        <Card key={property.id} className="overflow-hidden">
                            <div className="flex flex-col md:flex-row">
                                <div className="md:w-1/3">
                                    <img
                                        src={property.image}
                                        alt={property.title}
                                        className="w-full h-48 md:h-full object-cover"
                                    />
                                </div>

                                <div className="flex-1 p-6">
                                    <CardHeader className="p-0 mb-4">
                                        <div className="flex justify-between items-start">
                                            <div>
                                                <CardTitle className="text-xl font-semibold">{property.title}</CardTitle>
                                                <div className="flex items-center mt-2 text-gray-600">
                                                    <MapPin className="h-4 w-4 mr-1" />
                                                    <span>{property.location}</span>
                                                </div>
                                            </div>
                                            <div className="text-right">
                                                <p className="text-2xl font-bold text-[#e56b4e]">
                                                    R$ {property.price.toLocaleString('pt-BR')}
                                                </p>
                                                <Badge
                                                    variant={property.status === 'Ativa' ? 'default' : 'secondary'}
                                                    className={property.status === 'Ativa' ? 'bg-green-500' : ''}
                                                >
                                                    {property.status}
                                                </Badge>
                                            </div>
                                        </div>
                                    </CardHeader>

                                    <CardContent className="p-0">
                                        <div className="flex items-center gap-4 mb-4">
                                            <div className="flex items-center text-gray-600">
                                                <Bed className="h-4 w-4 mr-1" />
                                                <span className="text-sm">{property.bedrooms} quartos</span>
                                            </div>
                                            <div className="flex items-center text-gray-600">
                                                <Bath className="h-4 w-4 mr-1" />
                                                <span className="text-sm">{property.bathrooms} banheiros</span>
                                            </div>
                                            <div className="flex items-center text-gray-600">
                                                <Home className="h-4 w-4 mr-1" />
                                                <span className="text-sm">{property.area} m²</span>
                                            </div>
                                        </div>

                                        <div className="flex items-center gap-6 mb-4 text-sm text-gray-600">
                                            <div className="flex items-center">
                                                <Eye className="h-4 w-4 mr-1" />
                                                <span>{property.views} visualizações</span>
                                            </div>
                                            <div>
                                                <span className="font-medium">{property.proposals} propostas</span>
                                            </div>
                                        </div>

                                        <div className="flex gap-3">
                                            <Link href={`/edit-property/${property.id}`} className="flex-1">
                                                <Button variant="outline" className="w-full">
                                                    <Edit className="h-4 w-4 mr-2" />
                                                    Editar
                                                </Button>
                                            </Link>
                                            <Button className="flex-1 bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                                <TrendingUp className="h-4 w-4 mr-2" />
                                                Impulsionar
                                            </Button>
                                            <Link href={`/properties/${property.id}`}>
                                                <Button variant="ghost" size="icon">
                                                    <Eye className="h-4 w-4" />
                                                </Button>
                                            </Link>
                                        </div>
                                    </CardContent>
                                </div>
                            </div>
                        </Card>
                    ))}
                </div>

                {properties.length === 0 && (
                    <div className="text-center py-12">
                        <Home className="h-16 w-16 mx-auto text-gray-400 mb-4" />
                        <h3 className="text-xl font-semibold text-gray-600 mb-2">
                            Nenhuma propriedade encontrada
                        </h3>
                        <p className="text-gray-500 mb-6">
                            Você ainda não tem propriedades anunciadas.
                        </p>
                        <Link href="/add-property">
                            <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                Adicionar Primeira Propriedade
                            </Button>
                        </Link>
                    </div>
                )}
            </div>
        </MainLayout>
    );
};

export default MyPropertiesPage;