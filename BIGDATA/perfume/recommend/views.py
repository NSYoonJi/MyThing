from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import Perfume, Review
from django.core.cache import cache
from .serializers import PerfumeSerializer
# Create your views here.

import pandas as pd
import numpy as np
import ast
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
import time
from django_pandas.io import read_frame
from sklearn.neighbors import NearestNeighbors



# 취향조사 결과에서 나온 한 향수 기반 유사한 향수 20가지 추천
@api_view(['GET'])
def survey(request, perfume_id):
    if request.method == 'GET':
        perfumes = Perfume.objects.all()
        name = Perfume.objects.get(pk = perfume_id)
        
        perfume_list = list(perfumes)
        data = []
        for perfume in perfume_list:
            dummy = ast.literal_eval(perfume.notes)
            # 내부 리스트의 원소들을 하나의 1차원 리스트로 합치기d
            numbers = [str(n) for lst in dummy for n in lst]
            # 각 숫자를 따옴표로 묶어서 출력
            data.append(', '.join(f"'{n}'" for n in numbers))
        tfidf = TfidfVectorizer(stop_words='english')
        tfidf_matrix = tfidf.fit_transform(data)
        print('TF-IDF 행렬의 크기(shape) :',tfidf_matrix.shape)
        cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)
        print('코사인 유사도 연산 결과 :',cosine_sim.shape)

        # KNN 알고리즘으로 유사도 계산
        knn_model = NearestNeighbors(n_neighbors=12, metric='cosine')
        knn_model.fit(tfidf_matrix)
        idx = perfume_list.index(name)
        distances, indices = knn_model.kneighbors(tfidf_matrix[idx], n_neighbors=13)

        print('TF-IDF 알고리즘으로 계산된 유사도의 정확도 :', cosine_sim.mean() * 1000)
        # 정확도 계산
        accuracy = (1 - distances.mean()) * 100
        print('KNN 알고리즘으로 계산된 유사도의 정확도 :', accuracy)


        # 각 알고리즘에 부여할 가중치 설정
        tfidf_weight = 0.7
        knn_weight = 0.3

        # 앙상블 기법으로 유사도 계산
        ensemble_sim = (tfidf_weight * cosine_sim) + (knn_weight * knn_model.kneighbors_graph(tfidf_matrix).toarray())

        # 가장 유사한 12개의 향수 추천
        sim_scores = list(enumerate(ensemble_sim[idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
        reverse_sim_scores = sim_scores[1:13]
        perfume_indices = [idx[0] for idx in reverse_sim_scores]

        # 가장 유사한 12개의 향수 이름을 리턴한다.
        results = [perfume_list[i] for i in perfume_indices]
        recommends = [result.perfume_id for result in results]

        # 앙상블 정확도 계산
        ensemble_accuracy = (1 - distances.mean() * knn_weight + cosine_sim.mean() * tfidf_weight) * 100
        print('앙상블 기법으로 계산된 유사도의 정확도 :', ensemble_accuracy)

    return Response(recommends)

# 취향조사 결과에서 나온 한 향수 기반 반대되는 향수 20가지 추천
@api_view(['GET'])
def survey_reverse(request, perfume_id):
    if request.method == 'GET':
        perfumes = Perfume.objects.all()
        name = Perfume.objects.get(pk = perfume_id)
        
        perfume_list = list(perfumes)
        data = []
        for perfume in perfume_list:
            dummy = ast.literal_eval(perfume.notes)
            # 내부 리스트의 원소들을 하나의 1차원 리스트로 합치기d
            numbers = [str(n) for lst in dummy for n in lst]
            # 각 숫자를 따옴표로 묶어서 출력
            data.append(', '.join(f"'{n}'" for n in numbers))
        tfidf = TfidfVectorizer(stop_words='english')
        tfidf_matrix = tfidf.fit_transform(data)
        print('TF-IDF 행렬의 크기(shape) :',tfidf_matrix.shape)
        cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)
        print('코사인 유사도 연산 결과 :',cosine_sim.shape)

        # KNN 알고리즘으로 유사도 계산
        knn_model = NearestNeighbors(n_neighbors=12, metric='cosine')
        knn_model.fit(tfidf_matrix)
        idx = perfume_list.index(name)
        distances, indices = knn_model.kneighbors(tfidf_matrix[idx], n_neighbors=13)

        print('TF-IDF 알고리즘으로 계산된 유사도의 정확도 :', cosine_sim.mean() * 1000)
        # 정확도 계산
        accuracy = (1 - distances.mean()) * 100
        print('KNN 알고리즘으로 계산된 유사도의 정확도 :', accuracy)


        # 각 알고리즘에 부여할 가중치 설정
        tfidf_weight = 0.7
        knn_weight = 0.3

        # 앙상블 기법으로 유사도 계산
        ensemble_sim = (tfidf_weight * cosine_sim) + (knn_weight * knn_model.kneighbors_graph(tfidf_matrix).toarray())

        # 가장 유사하지 않은 12개의 향수 추천
        sim_scores = list(enumerate(ensemble_sim[idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=False)
        reverse_sim_scores = sim_scores[1:13]
        perfume_indices = [idx[0] for idx in reverse_sim_scores]

        # 가장 유사하지 않은 12개의 향수 이름을 리턴한다.
        results = [perfume_list[i] for i in perfume_indices]
        recommends = [result.perfume_id for result in results]

        # 앙상블 정확도 계산
        ensemble_accuracy = (1 - distances.mean() * knn_weight + cosine_sim.mean() * tfidf_weight) * 100
        print('앙상블 기법으로 계산된 유사도의 정확도 :', ensemble_accuracy)

    return Response(recommends)

# 향수 하나를 받아서 그 향수를 평가한 유저 기반 비슷한 향수  추천
@api_view(['GET'])
def recommend_review(request, perfume_id):
    if request.method == 'GET':
        reviews = Review.objects.all()
        df = read_frame(reviews)
        
        # pivot table 생성
        perfume_user = df.groupby(['perfume_id', 'username'])['rating'].sum().unstack(fill_value=0)
        
        # 코사인 유사도 계산
        item_based_collabor = cosine_similarity(perfume_user)
        # 코사인 유사도를 가지고 각 향수별 유사도 측정 
        item_based_collabor = pd.DataFrame(data = item_based_collabor, index = perfume_user.index, columns = perfume_user.index)
        def get_item_based_collabor(title):
            return item_based_collabor[title].sort_values(ascending = False)[1:12]
        
        data = get_item_based_collabor(perfume_id)
        
        print(item_based_collabor[601])
        return Response(data.index)

# 선호하는 향 기반 향수 추천
# 받는 데이터
#  {
#     "top_notes":["92", "155"],
#     "middle_notes":["147", "243", "116"],
#     "base_notes":["nan"]
# }
@api_view(['POST'])
def prefer(request):
    if request.method == 'POST':
        # 모든 향수 데이터 가져오기
        perfumes = Perfume.objects.all()
        perfume_list = list(perfumes)

        # 각 노트들에 들어있는 향 하나로 합치기
        user_notes = str(", ".join(request.data.get("top_notes"))) + ", " + str(", ".join(request.data.get("middle_notes"))) + ", " + str(", ".join(request.data.get("base_notes")))
        
        # 모든 향수 데이터에서 노트들 하나로 합치기
        data = []
        for perfume in perfume_list:
            dummy = ast.literal_eval(perfume.notes)
            # 내부 리스트의 원소들을 하나의 1차원 리스트로 합치기
            numbers = [str(n) for lst in dummy for n in lst]
            # 각 숫자를 따옴표로 묶어서 출력
            data.append(', '.join(f"'{n}'" for n in numbers))

        # 모든 데이터 행렬로 초기 길이 찾기
        tfidf = TfidfVectorizer(stop_words='english')
        tfidf_matrix = tfidf.fit_transform(data)
        print('TF-IDF 행렬의 크기(shape) :',tfidf_matrix.shape)
        # 유저가 선택한 향을 향수 노트 마지막에 추가해준 인덱스 번호
        last_index = len(data)

        data.append(user_notes)
        tfidf_matrix = tfidf.fit_transform(data)
        print('TF-IDF 행렬의 크기(shape) :',tfidf_matrix.shape)
        cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)
        print('코사인 유사도 연산 결과 :',cosine_sim.shape)
        
        def get_recommendations(idx, cosine_sim=cosine_sim):
            # 해당 향수 모든 향수와의 유사도를 가져온다.
            sim_scores = list(enumerate(cosine_sim[idx]))

            # 유사도에 따라 향수들을 정렬한다.
            sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)

            # 가장 유사한 20개의 향수를 받아온다.
            reverse_sim_scores = sim_scores[0:13]
            
            # 가장 유사한 20개의 향수의 인덱스를 얻는다.
            perfume_indices = [idx[0] for idx in reverse_sim_scores]
            # 마지막 넣은 인덱스 넣은거 있으면 지워준다.
            if last_index in perfume_indices:
                perfume_indices.remove(last_index)
            print(perfume_indices)
            # 가장 유사한 20개의 향수의 이름을 리턴한다.
            results = [perfume_list[i] for i in perfume_indices]
            perfumes = [result.perfume_id for result in results]
            return perfumes

        recommends = get_recommendations(last_index)

        print(recommends)
        # serializer = PerfumeSerializer(recommends, many = True)
    return Response(recommends)