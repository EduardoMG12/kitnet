"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { ServerCrash, Clock, Wifi, WifiOff, ArrowLeft, HomeIcon, RotateCcw } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const ServiceUnavalibleContent = () => {
    const [serverState, setServerState] = useState<number>(0);
    const [loadingDots, setLoadingDots] = useState<string>(".");
    const [isOnline, setIsOnline] = useState<boolean>(true);

    useEffect(() => {
        console.error("503 Error: Serviço temporariamente indisponível");

        const stateInterval = setInterval(() => {
            setServerState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 1800);

        const dotsInterval = setInterval(() => {
            setLoadingDots((prev) => {
                if (prev.length >= 3) return ".";
                return `${prev}.`;
            });
        }, 500);

        const wifiInterval = setInterval(() => {
            setIsOnline((prev) => !prev);
        }, 2500);

        return () => {
            clearInterval(stateInterval);
            clearInterval(dotsInterval);
            clearInterval(wifiInterval);
        };
    }, []);

    const getServerIcon = () => {
        switch (serverState) {
            case 0:
                return <ServerCrash className="h-20 w-20 text-blue-500 animate-pulse" />;
            case 1:
                return <Clock className="h-20 w-20 text-yellow-500 animate-bounce" />;
            default:
                return isOnline ?
                    <Wifi className="h-20 w-20 text-green-500 animate-ping" /> :
                    <WifiOff className="h-20 w-20 text-red-500 animate-pulse" />;
        }
    };

    const getCurrentTime = () => {
        return new Date().toLocaleTimeString('pt-BR');
    };

    return (
        <MainLayout hideFooter>
            <div className="min-h-[calc(100vh-80px)] flex flex-col items-center justify-center bg-gradient-to-b from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-blue-900/20 p-4">
                <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-blue-200 dark:border-blue-700">
                    <div className="flex flex-col items-center text-center">
                        <div className="mb-4">
                            {getServerIcon()}
                        </div>

                        <h1 className="text-6xl font-bold mb-2 text-blue-600 dark:text-blue-400">503</h1>
                        <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                            Servidor de Férias! 🏖️
                        </h2>

                        <p className="text-gray-600 dark:text-gray-400 mb-6">
                            Nosso servidor decidiu tirar uma soneca ou está numa fila gigante do SUS.
                            Volte mais tarde, ele promete estar de volta! 😴
                        </p>

                        <div className="w-full mb-6 p-4 bg-blue-50 dark:bg-blue-900/20 rounded-lg border border-blue-200 dark:border-blue-700">
                            <p className="text-sm text-blue-700 dark:text-blue-300 mb-3">
                                ⏱️ Status do servidor:
                            </p>
                            <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs space-y-2">
                                <div className="flex items-center justify-between">
                                    <span>Carregando{loadingDots}</span>
                                    <span className="text-yellow-500 animate-spin">⏳</span>
                                </div>
                                <div className="flex items-center justify-between">
                                    <span>Conexão:</span>
                                    <span className={isOnline ? "text-green-500" : "text-red-500"}>
                                        {isOnline ? "🟢 Online" : "🔴 Offline"}
                                    </span>
                                </div>
                                <div className="flex items-center justify-between">
                                    <span>Última vez visto:</span>
                                    <span className="text-gray-500">{getCurrentTime()}</span>
                                </div>
                                <div className="text-center text-xs text-gray-500 mt-2 border-t pt-2">
                                    "Estou ocupado, ligue mais tarde" - Servidor, provavelmente
                                </div>
                            </div>
                        </div>

                        <div className="space-y-4 w-full">
                            <div className="p-4 bg-yellow-50 dark:bg-yellow-900/20 rounded-lg">
                                <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                    🎯 Enquanto espera, que tal:
                                </p>
                                <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                    <div className="flex items-center">
                                        <span className="mr-2">☕</span>
                                        Fazer um café (vai demorar mesmo)
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🧘</span>
                                        Meditar sobre a impermanência da tecnologia
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">📱</span>
                                        Checar se sua internet funciona
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🎮</span>
                                        Jogar paciência enquanto espera
                                    </div>
                                </div>
                            </div>

                            <div className="flex flex-col sm:flex-row gap-3 justify-center">
                                <Button
                                    onClick={() => window.history.back()}
                                    variant="outline"
                                    className="flex-1"
                                >
                                    <ArrowLeft className="h-4 w-4 mr-2" />
                                    Desistir
                                </Button>
                                <Button
                                    onClick={() => window.location.reload()}
                                    variant="secondary"
                                    className="flex-1"
                                >
                                    <RotateCcw className="h-4 w-4 mr-2" />
                                    Insistir
                                </Button>
                                <Link href="/" className="flex-1">
                                    <Button className="w-full bg-blue-500 hover:bg-blue-600">
                                        <HomeIcon className="h-4 w-4 mr-2" />
                                        Fugir
                                    </Button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </Card>

                <div className="mt-8 text-center">
                    <p className="text-gray-600 dark:text-gray-400 text-sm">
                        💡 Dica: Geralmente resolve em alguns minutos... ou algumas horas... ou dias 🤷‍♂️
                    </p>
                </div>
            </div>
        </MainLayout>
    );
};

export default ServiceUnavalibleContent;