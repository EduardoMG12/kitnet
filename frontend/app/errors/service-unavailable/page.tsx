import ServiceUnavalibleContent from "@/components/pages/errors/ServiceUnavalibleContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "503 - Serviço Indisponível | Kitnet",
    description: "Pedimos desculpas, nossos servidores estão temporariamente sobrecarregados ou em manutenção. Por favor, tente novamente em breve.",
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
        url: 'https://www.kitnet.com.br/errors/service-unavailable', // Ajuste a URL se for diferente
        title: '503 - Serviço Indisponível | Kitnet',
        description: 'Nossos servidores estão temporariamente indisponíveis. Tente novamente mais tarde.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use sua imagem de erro genérica
                width: 800,
                height: 600,
                alt: 'Kitnet - Serviço Indisponível',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '503 - Serviço Indisponível | Kitnet',
        description: 'Nossos servidores estão temporariamente indisponíveis. Tente novamente mais tarde.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function ServiceUnavaliblePage() {
    return (
        <ServiceUnavalibleContent />
    );
}

