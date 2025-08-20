'use client';


import MainLayout from "@/components/layout/MainLayout";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Separator } from "@/components/ui/separator";
import { Upload, CheckCircle, Home, MapPin, Building, ArrowRight, X } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";

const AddPropertyPage = () => {
    const [step, setStep] = useState(1);
    const [images, setImages] = useState<string[]>([]);
    const [uploadProgress, setUploadProgress] = useState(0);
    const totalSteps = 4;

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            // Simulate upload progress
            const interval = setInterval(() => {
                setUploadProgress((prev) => {
                    if (prev >= 100) {
                        clearInterval(interval);
                        return 100;
                    }
                    return prev + 10;
                });
            }, 300);

            // For demo purposes, we're using a fixed image
            setTimeout(() => {
                setImages((prev) => [
                    ...prev,
                    "https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=300&h=300&q=80"
                ]);
                setUploadProgress(0);
            }, 3000);
        }
    };

    const removeImage = (index: number) => {
        setImages((prev) => prev.filter((_, i) => i !== index));
    };

    const nextStep = () => {
        if (step < totalSteps) {
            setStep(step + 1);
            window.scrollTo(0, 0);
        } else {
            // Submit form
            toast(
                "Imóvel cadastrado com sucesso! Seu imóvel está sendo avaliado e em breve estará disponível na plataforma."
                //     {
                //     title: "Imóvel cadastrado com sucesso!",
                //     description: "Seu imóvel está sendo avaliado e em breve estará disponível na plataforma.",
                // }
            );
        }
    };

    const prevStep = () => {
        if (step > 1) {
            setStep(step - 1);
            window.scrollTo(0, 0);
        }
    };

    return (
        <MainLayout>
            <div className="py-12 px-4 sm:px-6 lg:px-8 bg-[#f9f4e8]">
                <div className="max-w-3xl mx-auto">
                    <div className="text-center mb-8">
                        <h1 className="text-3xl font-heading font-bold text-[#2c3e50] mb-2">
                            Anuncie seu imóvel
                        </h1>
                        <p className="text-muted-foreground">
                            Preencha as informações abaixo para cadastrar seu imóvel na plataforma.
                        </p>
                    </div>

                    <div className="mb-8">
                        <div className="flex items-center justify-between">
                            {[1, 2, 3, 4].map((s) => (
                                <div key={s} className="flex flex-col items-center">
                                    <div
                                        className={`w-10 h-10 rounded-full flex items-center justify-center mb-2 
                    ${s < step ? "bg-[#e56b4e] text-white" :
                                                s === step ? "bg-[#e56b4e]/20 border-2 border-[#e56b4e] text-[#e56b4e]" :
                                                    "bg-gray-100 text-muted-foreground"
                                            }`}
                                    >
                                        {s < step ? (
                                            <CheckCircle className="h-5 w-5" />
                                        ) : (
                                            s
                                        )}
                                    </div>
                                    <span className="text-xs hidden sm:block">{getStepLabel(s)}</span>
                                </div>
                            ))}
                        </div>

                        <div className="relative mt-2">
                            <div className="absolute h-1 bg-gray-200 w-full top-0" />
                            <div
                                className="absolute h-1 bg-[#e56b4e] transition-all duration-300"
                                style={{ width: `${(step - 1) * 100 / (totalSteps - 1)}%` }}
                            />
                        </div>
                    </div>

                    <div className="bg-white rounded-lg shadow-md p-8">
                        {step === 1 && (
                            <div className="space-y-6">
                                <h2 className="text-xl font-heading font-medium mb-4 flex items-center">
                                    <Home className="mr-2 h-5 w-5 text-[#e56b4e]" />
                                    Informações básicas
                                </h2>

                                <div>
                                    <Label htmlFor="property-type" className="text-sm font-medium">
                                        Tipo de imóvel
                                    </Label>
                                    <Select>
                                        <SelectTrigger className="mt-1">
                                            <SelectValue placeholder="Selecione o tipo de imóvel" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="apartment">Apartamento</SelectItem>
                                            <SelectItem value="house">Casa</SelectItem>
                                            <SelectItem value="studio">Studio</SelectItem>
                                            <SelectItem value="land">Terreno</SelectItem>
                                            <SelectItem value="commercial">Comercial</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <div>
                                    <Label htmlFor="title" className="text-sm font-medium">
                                        Título do anúncio
                                    </Label>
                                    <Input
                                        id="title"
                                        placeholder="Ex: Apartamento moderno próximo ao metrô"
                                        className="mt-1"
                                    />
                                    <p className="text-xs text-muted-foreground mt-1">
                                        Um bom título aumenta a visibilidade do seu anúncio
                                    </p>
                                </div>

                                <div>
                                    <Label htmlFor="description" className="text-sm font-medium">
                                        Descrição
                                    </Label>
                                    <Textarea
                                        id="description"
                                        placeholder="Descreva seu imóvel em detalhes..."
                                        className="mt-1"
                                        rows={6}
                                    />
                                </div>

                                <div>
                                    <Label className="text-sm font-medium">
                                        Finalidade
                                    </Label>
                                    <RadioGroup defaultValue="rent" className="flex space-x-4 mt-1">
                                        <div className="flex items-center space-x-2">
                                            <RadioGroupItem value="rent" id="rent" />
                                            <label htmlFor="rent">Aluguel</label>
                                        </div>
                                        <div className="flex items-center space-x-2">
                                            <RadioGroupItem value="sale" id="sale" />
                                            <label htmlFor="sale">Venda</label>
                                        </div>
                                        <div className="flex items-center space-x-2">
                                            <RadioGroupItem value="both" id="both" />
                                            <label htmlFor="both">Ambos</label>
                                        </div>
                                    </RadioGroup>
                                </div>

                                <div>
                                    <Label htmlFor="price" className="text-sm font-medium">
                                        Valor do aluguel (R$/mês)
                                    </Label>
                                    <Input
                                        id="price"
                                        type="number"
                                        placeholder="0,00"
                                        className="mt-1"
                                    />
                                </div>
                            </div>
                        )}

                        {step === 2 && (
                            <div className="space-y-6">
                                <h2 className="text-xl font-heading font-medium mb-4 flex items-center">
                                    <MapPin className="mr-2 h-5 w-5 text-[#e56b4e]" />
                                    Localização
                                </h2>

                                <div>
                                    <Label htmlFor="zipcode" className="text-sm font-medium">
                                        CEP
                                    </Label>
                                    <Input
                                        id="zipcode"
                                        placeholder="00000-000"
                                        className="mt-1"
                                    />
                                </div>

                                <div className="grid grid-cols-2 gap-4">
                                    <div>
                                        <Label htmlFor="state" className="text-sm font-medium">
                                            Estado
                                        </Label>
                                        <Select>
                                            <SelectTrigger className="mt-1">
                                                <SelectValue placeholder="Selecione" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectItem value="sp">São Paulo</SelectItem>
                                                <SelectItem value="rj">Rio de Janeiro</SelectItem>
                                                <SelectItem value="mg">Minas Gerais</SelectItem>
                                                <SelectItem value="rs">Rio Grande do Sul</SelectItem>
                                                {/* Add other states */}
                                            </SelectContent>
                                        </Select>
                                    </div>
                                    <div>
                                        <Label htmlFor="city" className="text-sm font-medium">
                                            Cidade
                                        </Label>
                                        <Input
                                            id="city"
                                            placeholder="Cidade"
                                            className="mt-1"
                                        />
                                    </div>
                                </div>

                                <div>
                                    <Label htmlFor="neighborhood" className="text-sm font-medium">
                                        Bairro
                                    </Label>
                                    <Input
                                        id="neighborhood"
                                        placeholder="Bairro"
                                        className="mt-1"
                                    />
                                </div>

                                <div>
                                    <Label htmlFor="address" className="text-sm font-medium">
                                        Endereço
                                    </Label>
                                    <Input
                                        id="address"
                                        placeholder="Rua, Avenida, etc."
                                        className="mt-1"
                                    />
                                </div>

                                <div className="grid grid-cols-2 gap-4">
                                    <div>
                                        <Label htmlFor="number" className="text-sm font-medium">
                                            Número
                                        </Label>
                                        <Input
                                            id="number"
                                            placeholder="000"
                                            className="mt-1"
                                        />
                                    </div>
                                    <div>
                                        <Label htmlFor="complement" className="text-sm font-medium">
                                            Complemento
                                        </Label>
                                        <Input
                                            id="complement"
                                            placeholder="Apto, Bloco, etc."
                                            className="mt-1"
                                        />
                                    </div>
                                </div>

                                <div className="flex items-center space-x-2">
                                    <Checkbox id="hide-exact-address" />
                                    <label
                                        htmlFor="hide-exact-address"
                                        className="text-sm text-muted-foreground"
                                    >
                                        Não mostrar o endereço exato no anúncio (apenas o bairro)
                                    </label>
                                </div>
                            </div>
                        )}

                        {step === 3 && (
                            <div className="space-y-6">
                                <h2 className="text-xl font-heading font-medium mb-4 flex items-center">
                                    <Building className="mr-2 h-5 w-5 text-[#e56b4e]" />
                                    Características
                                </h2>

                                <div className="grid grid-cols-2 gap-4">
                                    <div>
                                        <Label htmlFor="area" className="text-sm font-medium">
                                            Área total (m²)
                                        </Label>
                                        <Input
                                            id="area"
                                            type="number"
                                            placeholder="0"
                                            className="mt-1"
                                        />
                                    </div>
                                    <div>
                                        <Label htmlFor="built-area" className="text-sm font-medium">
                                            Área construída (m²)
                                        </Label>
                                        <Input
                                            id="built-area"
                                            type="number"
                                            placeholder="0"
                                            className="mt-1"
                                        />
                                    </div>
                                </div>

                                <div className="grid grid-cols-3 gap-4">
                                    <div>
                                        <Label htmlFor="bedrooms" className="text-sm font-medium">
                                            Quartos
                                        </Label>
                                        <Select>
                                            <SelectTrigger className="mt-1">
                                                <SelectValue placeholder="Selecione" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectItem value="0">0</SelectItem>
                                                <SelectItem value="1">1</SelectItem>
                                                <SelectItem value="2">2</SelectItem>
                                                <SelectItem value="3">3</SelectItem>
                                                <SelectItem value="4">4+</SelectItem>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                    <div>
                                        <Label htmlFor="bathrooms" className="text-sm font-medium">
                                            Banheiros
                                        </Label>
                                        <Select>
                                            <SelectTrigger className="mt-1">
                                                <SelectValue placeholder="Selecione" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectItem value="1">1</SelectItem>
                                                <SelectItem value="2">2</SelectItem>
                                                <SelectItem value="3">3</SelectItem>
                                                <SelectItem value="4">4+</SelectItem>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                    <div>
                                        <Label htmlFor="parking" className="text-sm font-medium">
                                            Vagas de garagem
                                        </Label>
                                        <Select>
                                            <SelectTrigger className="mt-1">
                                                <SelectValue placeholder="Selecione" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectItem value="0">0</SelectItem>
                                                <SelectItem value="1">1</SelectItem>
                                                <SelectItem value="2">2</SelectItem>
                                                <SelectItem value="3">3</SelectItem>
                                                <SelectItem value="4">4+</SelectItem>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                </div>

                                <Separator />

                                <div>
                                    <Label className="text-sm font-medium mb-2">
                                        Comodidades
                                    </Label>
                                    <div className="grid grid-cols-2 md:grid-cols-3 gap-3 mt-1">
                                        {[
                                            "Mobiliado", "Piscina", "Churrasqueira", "Academia", "Salão de festas",
                                            "Ar condicionado", "Área de serviço", "Pet friendly", "Portaria 24h",
                                            "Elevador", "Internet fibra", "Varanda"
                                        ].map((item) => (
                                            <div key={item} className="flex items-center space-x-2">
                                                <Checkbox id={item.toLowerCase().replace(/\s/g, "-")} />
                                                <label htmlFor={item.toLowerCase().replace(/\s/g, "-")}>
                                                    {item}
                                                </label>
                                            </div>
                                        ))}
                                    </div>
                                </div>

                                <div>
                                    <Label htmlFor="floor" className="text-sm font-medium">
                                        Andar (se aplicável)
                                    </Label>
                                    <Input
                                        id="floor"
                                        type="number"
                                        placeholder="0"
                                        className="mt-1"
                                    />
                                </div>

                                <div>
                                    <Label htmlFor="condo-fee" className="text-sm font-medium">
                                        Valor do condomínio (R$)
                                    </Label>
                                    <Input
                                        id="condo-fee"
                                        type="number"
                                        placeholder="0,00"
                                        className="mt-1"
                                    />
                                </div>
                            </div>
                        )}

                        {step === 4 && (
                            <div className="space-y-6">
                                <h2 className="text-xl font-heading font-medium mb-4 flex items-center">
                                    <Upload className="mr-2 h-5 w-5 text-[#e56b4e]" />
                                    Fotos
                                </h2>

                                <div className="border-2 border-dashed rounded-lg p-6 text-center">
                                    <Upload className="h-10 w-10 text-muted-foreground mb-2 mx-auto" />
                                    <p className="text-base font-medium mb-1">
                                        Arraste ou clique para enviar fotos
                                    </p>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        JPG, PNG ou WEBP (máx. 10MB por imagem)
                                    </p>
                                    <Button
                                        variant="outline"
                                        onClick={() => document.getElementById("image-upload")?.click()}
                                    >
                                        Selecionar arquivo
                                    </Button>
                                    <input
                                        id="image-upload"
                                        type="file"
                                        accept="image/*"
                                        className="hidden"
                                        onChange={handleImageUpload}
                                        multiple
                                    />
                                </div>

                                {uploadProgress > 0 && uploadProgress < 100 && (
                                    <div className="mt-4">
                                        <p className="text-sm mb-1">Enviando imagem...</p>
                                        <div className="w-full bg-gray-200 rounded-full h-2.5">
                                            <div
                                                className="bg-[#e56b4e] h-2.5 rounded-full"
                                                style={{ width: `${uploadProgress}%` }}
                                            />
                                        </div>
                                    </div>
                                )}

                                {images.length > 0 && (
                                    <div>
                                        <h3 className="font-medium text-sm mb-2">
                                            Imagens enviadas ({images.length})
                                        </h3>
                                        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
                                            {images.map((img) => (
                                                <div key={img} className="relative group">
                                                    <img
                                                        src={img}
                                                        alt={"Property view"}
                                                        className="w-full h-24 object-cover rounded-md"
                                                    />
                                                    <button
                                                        type="button"
                                                        className="absolute top-1 right-1 bg-white/80 rounded-full p-1 opacity-0 group-hover:opacity-100 transition-opacity"
                                                        onClick={() => removeImage(images.indexOf(img))}
                                                    >
                                                        <X className="h-4 w-4" />
                                                    </button>
                                                    {images.indexOf(img) === 0 && (
                                                        <div className="absolute bottom-1 left-1 bg-[#e56b4e] text-white text-xs px-2 py-0.5 rounded">
                                                            Capa
                                                        </div>
                                                    )}
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                )}

                                <div className="flex items-start space-x-2 mt-6">
                                    <Checkbox id="terms-property" />
                                    <div className="grid gap-1.5 leading-none">
                                        <label
                                            htmlFor="terms-property"
                                            className="text-sm text-muted-foreground leading-relaxed"
                                        >
                                            Confirmo que sou o proprietário do imóvel ou estou autorizado a anunciá-lo, e concordo com os{" "}
                                            <a href="/terms" className="text-[#e56b4e] hover:underline">
                                                Termos de Uso
                                            </a>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        )}

                        <div className="mt-8 pt-6 border-t flex justify-between">
                            {step > 1 ? (
                                <Button variant="outline" onClick={prevStep}>
                                    Voltar
                                </Button>
                            ) : (
                                <div />
                            )}
                            <Button onClick={nextStep} className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                {step < totalSteps ? (
                                    <>
                                        Próximo
                                        <ArrowRight className="ml-2 h-4 w-4" />
                                    </>
                                ) : (
                                    "Finalizar cadastro"
                                )}
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

const getStepLabel = (step: number): string => {
    switch (step) {
        case 1:
            return "Informações básicas";
        case 2:
            return "Localização";
        case 3:
            return "Características";
        case 4:
            return "Fotos";
        default:
            return "";
    }
};

export default AddPropertyPage;