import BadGatewayContent from "@/components/errors/BadGatewayContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "502 - Bad Gateway | Kitnet",
    description: "Desculpe, nossos servidores não conseguiram se comunicar corretamente. Por favor, tente novamente em alguns instantes. Se o problema persistir, entre em contato com o suporte.",
    // Configurações para evitar indexação por mecanismos de busca
    robots: {
        index: false,
        follow: false,
        nocache: true,
        googleBot: {
            index: false,
            follow: false,
            noimageindex: true,
            'max-video-preview': -1,
            'max-snippet': -1,
        },
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/errors/bad-gateway', // Ajuste a URL se for diferente
        title: '502 - Bad Gateway | Kitnet',
        description: 'Erro de comunicação entre servidores. Tente novamente mais tarde.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use sua imagem de erro genérica
                width: 800,
                height: 600,
                alt: 'Kitnet - Bad Gateway',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '502 - Bad Gateway | Kitnet',
        description: 'Erro de comunicação entre servidores. Tente novamente mais tarde.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function BadGatewayPage() {
    return (
        <MainLayout hideFooter>
            <BadGatewayContent />
        </MainLayout>
    );
}